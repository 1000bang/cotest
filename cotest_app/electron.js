const { app, BrowserWindow, ipcMain } = require('electron');
const path = require('path');
const { exec } = require('child_process');
const fs = require('fs').promises;

let mainWindow;

// macOS에서 Electron 앱은 터미널과 다른 PATH를 가지므로 명시적으로 설정
const userPath = [
  '/opt/homebrew/bin',
  '/usr/local/bin',
  '/usr/bin',
  '/bin',
  '/usr/sbin',
  '/sbin',
  process.env.PATH
].join(':');

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 1400,
    height: 900,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false
    },
    titleBarStyle: 'hiddenInset'
  });

  mainWindow.loadFile(path.join(__dirname, 'src', 'index.html'));

  // 개발자 도구 (필요시 Cmd+Option+I로 열기)
}

app.whenReady().then(() => {
  createWindow();

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
      createWindow();
    }
  });
});

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

// IPC 핸들러: Java 테스트 실행 (javac + java 직접 실행, 원본 파일 안 건드림)
ipcMain.handle('run-java-test', async (event, { className, code, testCases }) => {
  const tmpDir = path.join(require('os').tmpdir(), 'cotest_temp');
  const pkgDir = path.join(tmpDir, 'com', 'codingtest');

  try {
    // 임시 디렉토리 생성
    await fs.mkdir(pkgDir, { recursive: true });

    // 사용자 코드를 임시 디렉토리에 저장
    const userFile = path.join(pkgDir, `${className}.java`);
    await fs.writeFile(userFile, code, 'utf8');

    // 기존 테스트 파일 읽어서 복사
    const testSrcPath = path.join(
      __dirname, '..', 'cotest_java',
      'src/test/java/com/codingtest',
      `${className}Test.java`
    );
    const testFile = path.join(pkgDir, `${className}Test.java`);

    // 테스트 파일을 JUnit 없는 main() 메서드로 변환
    const testContent = await fs.readFile(testSrcPath, 'utf8');
    const runnerCode = convertTestToMain(className, testContent);
    await fs.writeFile(testFile, runnerCode, 'utf8');

    // javac 컴파일
    const compileResult = await new Promise((resolve) => {
      exec(
        `cd "${tmpDir}" && javac com/codingtest/${className}.java com/codingtest/${className}Test.java`,
        { env: { ...process.env, PATH: userPath }, timeout: 15000 },
        (error, stdout, stderr) => {
          if (error) {
            resolve({ success: false, output: stderr || stdout });
          } else {
            resolve({ success: true });
          }
        }
      );
    });

    if (!compileResult.success) {
      return {
        success: false,
        output: compileResult.output,
        error: '컴파일 에러'
      };
    }

    // java 실행
    return new Promise((resolve) => {
      exec(
        `cd "${tmpDir}" && java com.codingtest.${className}Test`,
        { env: { ...process.env, PATH: userPath }, timeout: 10000 },
        (error, stdout, stderr) => {
          const output = stdout || '';
          const hasFailure = output.includes('FAIL') || error;
          resolve({
            success: !hasFailure,
            output: output + (stderr || '')
          });
        }
      );
    });
  } catch (error) {
    return { success: false, error: error.message };
  }
});

// JUnit 테스트 → main() 메서드 변환
function convertTestToMain(className, testContent) {
  // 테스트 파일에서 assertion 추출
  const lines = testContent.split('\n');

  // import와 class 선언 부분 추출
  const assertions = [];
  let inMethod = false;
  let methodName = '';
  let methodLines = [];

  for (const line of lines) {
    const trimmed = line.trim();

    // @DisplayName에서 테스트 이름 추출
    const displayMatch = trimmed.match(/@DisplayName\("(.+?)"\)/);
    if (displayMatch) {
      methodName = displayMatch[1];
      continue;
    }

    // @Test 무시
    if (trimmed === '@Test') {
      inMethod = true;
      methodLines = [];
      continue;
    }

    // void 메서드 시작
    if (inMethod && trimmed.match(/^void\s+\w+\(\)/)) {
      continue;
    }

    // 메서드 내부의 코드 수집
    if (inMethod) {
      if (trimmed === '}' && methodLines.length > 0) {
        assertions.push({ name: methodName, lines: [...methodLines] });
        inMethod = false;
        methodName = '';
        methodLines = [];
      } else if (trimmed !== '{' && trimmed !== '') {
        methodLines.push(trimmed);
      }
    }
  }

  // main() 메서드로 변환
  let code = `package com.codingtest;\n\nimport java.util.*;\n\npublic class ${className}Test {\n`;
  code += `    public static void main(String[] args) {\n`;
  code += `        ${className} sol = new ${className}();\n`;
  code += `        int passed = 0;\n`;
  code += `        int total = ${assertions.length};\n\n`;

  for (let i = 0; i < assertions.length; i++) {
    const test = assertions[i];
    const testName = test.name || `Test ${i + 1}`;

    code += `        // ${testName}\n`;
    code += `        try {\n`;

    for (const line of test.lines) {
      // solution 인스턴스 변수 참조를 sol로 변경
      let converted = line
        .replace(/private\s+final\s+.*/, '')
        .replace(/new\s+${className}\(\)/, '')
        .replace(/solution\.solution/g, 'sol.solution');

      // assertEquals → if 비교로 변환
      const assertEqMatch = converted.match(/assertEquals\((.+),\s*sol\.solution\((.+)\)\)/);
      if (assertEqMatch) {
        const expected = assertEqMatch[1].trim();
        const input = assertEqMatch[2].trim();
        code += `            Object result = sol.solution(${input});\n`;
        code += `            if (String.valueOf(result).equals(String.valueOf(${expected}))) {\n`;
        code += `                System.out.println("PASS: ${testName}");\n`;
        code += `                passed++;\n`;
        code += `            } else {\n`;
        code += `                System.out.println("FAIL: ${testName} - expected " + ${expected} + " but got " + result);\n`;
        code += `            }\n`;
        continue;
      }

      // assertArrayEquals → Arrays.equals로 변환
      const assertArrMatch = converted.match(/assertArrayEquals\((.+),\s*sol\.solution\((.+)\)\)/);
      if (assertArrMatch) {
        const expected = assertArrMatch[1].trim();
        const input = assertArrMatch[2].trim();
        code += `            var result = sol.solution(${input});\n`;
        code += `            if (java.util.Arrays.equals(result, ${expected})) {\n`;
        code += `                System.out.println("PASS: ${testName}");\n`;
        code += `                passed++;\n`;
        code += `            } else {\n`;
        code += `                System.out.println("FAIL: ${testName} - expected " + java.util.Arrays.toString(${expected}) + " but got " + java.util.Arrays.toString(result));\n`;
        code += `            }\n`;
        continue;
      }

      // assertTrue
      const assertTrueMatch = converted.match(/assertTrue\(sol\.solution\((.+)\)\)/);
      if (assertTrueMatch) {
        const input = assertTrueMatch[1].trim();
        code += `            boolean result = sol.solution(${input});\n`;
        code += `            if (result) {\n`;
        code += `                System.out.println("PASS: ${testName}");\n`;
        code += `                passed++;\n`;
        code += `            } else {\n`;
        code += `                System.out.println("FAIL: ${testName} - expected true but got false");\n`;
        code += `            }\n`;
        continue;
      }

      // assertFalse
      const assertFalseMatch = converted.match(/assertFalse\(sol\.solution\((.+)\)\)/);
      if (assertFalseMatch) {
        const input = assertFalseMatch[1].trim();
        code += `            boolean result = sol.solution(${input});\n`;
        code += `            if (!result) {\n`;
        code += `                System.out.println("PASS: ${testName}");\n`;
        code += `                passed++;\n`;
        code += `            } else {\n`;
        code += `                System.out.println("FAIL: ${testName} - expected false but got true");\n`;
        code += `            }\n`;
        continue;
      }

      // 그 외 라인은 그대로 (변수 선언 등)
      if (converted.trim() && !converted.includes('assert') && !converted.includes('private ')) {
        code += `            ${converted}\n`;
      }
    }

    code += `        } catch (Exception e) {\n`;
    code += `            System.out.println("FAIL: ${testName} - " + e.getMessage());\n`;
    code += `        }\n\n`;
  }

  code += `        System.out.println("\\n결과: " + passed + "/" + total + " 테스트 통과");\n`;
  code += `        if (passed == total) System.out.println("모든 테스트 통과!");\n`;
  code += `    }\n}\n`;

  return code;
}


// IPC 핸들러: Python 코드 실행
ipcMain.handle('run-python-code', async (event, { code }) => {
  try {
    const pyDir = path.join(__dirname, '..', 'cotest_py');
    const tempFile = path.join(pyDir, 'temp_solution.py');

    // 코드를 임시 파일로 저장
    await fs.writeFile(tempFile, code, 'utf8');

    // Python 실행
    return new Promise((resolve, reject) => {
      exec(`cd "${pyDir}" && python3 temp_solution.py`, { env: { ...process.env, PATH: userPath } }, (error, stdout, stderr) => {
        if (error) {
          resolve({
            success: false,
            output: stderr || stdout,
            error: error.message
          });
        } else {
          resolve({
            success: true,
            output: stdout
          });
        }
      });
    });
  } catch (error) {
    return {
      success: false,
      error: error.message
    };
  }
});

// IPC 핸들러: 문제 목록 로드
ipcMain.handle('load-problems', async () => {
  try {
    const problemsPath = path.join(__dirname, 'src/data/problems.json');
    console.log('문제 파일 경로:', problemsPath);
    const data = await fs.readFile(problemsPath, 'utf8');
    const problems = JSON.parse(data);
    console.log(`${problems.length}개 문제 로드 완료`);
    return problems;
  } catch (error) {
    console.error('문제 로딩 실패:', error);
    return [];
  }
});

// IPC 핸들러: 진행도 저장
ipcMain.handle('save-progress', async (event, progressData) => {
  try {
    const progressPath = path.join(__dirname, 'src/data/progress.json');
    let progress = {};

    try {
      const data = await fs.readFile(progressPath, 'utf8');
      progress = JSON.parse(data);
    } catch (e) {
      // 파일이 없으면 새로 생성
    }

    progress[progressData.id] = {
      ...progressData,
      lastAttempt: new Date().toISOString()
    };

    await fs.writeFile(progressPath, JSON.stringify(progress, null, 2), 'utf8');
    return { success: true };
  } catch (error) {
    return { success: false, error: error.message };
  }
});

// IPC 핸들러: 진행도 로드
ipcMain.handle('load-progress', async () => {
  try {
    const progressPath = path.join(__dirname, 'src/data/progress.json');
    const data = await fs.readFile(progressPath, 'utf8');
    return JSON.parse(data);
  } catch (error) {
    return {};
  }
});

// IPC 핸들러: Java 파일 읽기
ipcMain.handle('read-java-file', async (event, problemId) => {
  try {
    const javaFilePath = path.join(
      __dirname,
      '..',
      'cotest_java/src/main/java/com/codingtest',
      `${problemId}.java`
    );
    console.log('Java 파일 읽기:', javaFilePath);
    const content = await fs.readFile(javaFilePath, 'utf8');
    return { success: true, content };
  } catch (error) {
    console.error('Java 파일 읽기 실패:', error);
    return { success: false, error: error.message };
  }
});
