const fs = require('fs');
const path = require('path');

// electron.js의 convertTestToMain과 동일한 함수
function convertTestToMain(className, testContent) {
  const lines = testContent.split('\n');
  const assertions = [];
  let inMethod = false;
  let methodName = '';
  let methodLines = [];

  for (const line of lines) {
    const trimmed = line.trim();

    const displayMatch = trimmed.match(/@DisplayName\("(.+?)"\)/);
    if (displayMatch) {
      methodName = displayMatch[1];
      continue;
    }

    if (trimmed === '@Test') {
      inMethod = true;
      methodLines = [];
      continue;
    }

    if (inMethod && trimmed.match(/^void\s+\w+\(\)/)) {
      continue;
    }

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
      let converted = line
        .replace(/solution\.solution/g, 'sol.solution');

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

      // assertTrue/assertFalse
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

// 테스트 실행
const testDir = path.join(__dirname, '../cotest_java/src/test/java/com/codingtest');
const testFiles = fs.readdirSync(testDir).filter(f => f.endsWith('Test.java'));

let successCount = 0;

for (const file of testFiles) {
  const className = file.replace('Test.java', '');
  const testContent = fs.readFileSync(path.join(testDir, file), 'utf8');

  try {
    const result = convertTestToMain(className, testContent);
    // 임시 파일에 쓰기
    const tmpDir = '/tmp/cotest_temp/com/codingtest';
    fs.mkdirSync(tmpDir, { recursive: true });
    fs.writeFileSync(path.join(tmpDir, `${className}Test.java`), result);
    console.log(`✅ ${file} 변환 성공`);
    successCount++;
  } catch (e) {
    console.log(`❌ ${file} 변환 실패: ${e.message}`);
  }
}

console.log(`\n${successCount}/${testFiles.length} 파일 변환 성공`);
