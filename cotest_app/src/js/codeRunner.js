const { ipcRenderer: ipcRendererForRunner } = require('electron');

class CodeRunner {
  constructor() {
    this.isRunning = false;
  }

  async runJavaTest(className, code) {
    if (this.isRunning) {
      return { success: false, error: '이미 실행 중입니다.' };
    }

    this.isRunning = true;

    try {
      const result = await ipcRendererForRunner.invoke('run-java-test', {
        className,
        code
      });

      this.isRunning = false;
      return this.parseJavaTestResult(result);
    } catch (error) {
      this.isRunning = false;
      return {
        success: false,
        error: error.message
      };
    }
  }

  async runPythonCode(code) {
    if (this.isRunning) {
      return { success: false, error: '이미 실행 중입니다.' };
    }

    this.isRunning = true;

    try {
      const result = await ipcRendererForRunner.invoke('run-python-code', {
        code
      });

      this.isRunning = false;
      return this.parsePythonResult(result);
    } catch (error) {
      this.isRunning = false;
      return {
        success: false,
        error: error.message
      };
    }
  }

  parseJavaTestResult(result) {
    if (!result.success) {
      return {
        success: false,
        message: '❌ 컴파일 또는 실행 실패',
        output: result.output || result.error,
        details: this.extractErrorDetails(result.output || result.error)
      };
    }

    const output = result.output;

    // javac+java 직접 실행 출력 파싱
    // 형식: "결과: X/Y 테스트 통과" + "모든 테스트 통과!"
    const resultMatch = output.match(/결과: (\d+)\/(\d+) 테스트 통과/);

    if (resultMatch) {
      const passed = parseInt(resultMatch[1]);
      const total = parseInt(resultMatch[2]);
      const failed = total - passed;

      return {
        success: passed === total,
        message: passed === total ? '✅ 모든 테스트 통과!' : `❌ ${failed}개 테스트 실패`,
        output: output,
        stats: { total, passed, failed }
      };
    }

    // PASS/FAIL 카운트로 폴백
    const passCount = (output.match(/^PASS:/gm) || []).length;
    const failCount = (output.match(/^FAIL:/gm) || []).length;

    if (passCount + failCount > 0) {
      return {
        success: failCount === 0,
        message: failCount === 0 ? '✅ 모든 테스트 통과!' : `❌ ${failCount}개 테스트 실패`,
        output: output,
        stats: { total: passCount + failCount, passed: passCount, failed: failCount }
      };
    }

    return {
      success: false,
      message: '❌ 테스트 결과를 파싱할 수 없습니다',
      output: output
    };
  }

  parsePythonResult(result) {
    if (!result.success) {
      return {
        success: false,
        message: '실행 실패',
        output: result.output || result.error
      };
    }

    return {
      success: true,
      message: '✅ 실행 성공',
      output: result.output
    };
  }

  extractErrorDetails(output) {
    // 에러 메시지에서 중요한 부분 추출
    const lines = output.split('\n');
    const errorLines = [];

    for (let i = 0; i < lines.length; i++) {
      const line = lines[i];
      if (
        line.includes('error:') ||
        line.includes('Error:') ||
        line.includes('Exception') ||
        line.includes('Failed') ||
        line.includes('expected')
      ) {
        errorLines.push(line.trim());
      }
    }

    return errorLines.join('\n');
  }
}

// 전역 인스턴스
const codeRunner = new CodeRunner();
