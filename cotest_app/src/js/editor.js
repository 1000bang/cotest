class CodeEditor {
  constructor(textareaId) {
    this.textarea = document.getElementById(textareaId);
    this.currentLanguage = 'java';
    this.currentCode = {
      java: '',
      python: ''
    };

    this.setupEditor();
  }

  setupEditor() {
    // 탭 키 지원
    this.textarea.addEventListener('keydown', (e) => {
      if (e.key === 'Tab') {
        e.preventDefault();
        const start = this.textarea.selectionStart;
        const end = this.textarea.selectionEnd;
        const value = this.textarea.value;

        this.textarea.value = value.substring(0, start) + '    ' + value.substring(end);
        this.textarea.selectionStart = this.textarea.selectionEnd = start + 4;
      }
    });

    // 자동 저장 (3초마다)
    setInterval(() => {
      this.saveCurrentCode();
    }, 3000);
  }

  setLanguage(language) {
    // 현재 코드 저장
    this.saveCurrentCode();

    // 언어 변경
    this.currentLanguage = language;

    // 저장된 코드 불러오기
    this.loadCurrentCode();
  }

  saveCurrentCode() {
    this.currentCode[this.currentLanguage] = this.textarea.value;
  }

  loadCurrentCode() {
    this.textarea.value = this.currentCode[this.currentLanguage] || '';
  }

  setCode(code) {
    this.currentCode[this.currentLanguage] = code;
    this.textarea.value = code;
  }

  getCode() {
    return this.textarea.value;
  }

  reset() {
    this.currentCode = {
      java: '',
      python: ''
    };
    this.textarea.value = '';
  }

  insertTemplate(template) {
    this.setCode(template);
  }

  async loadProblemCode(problemId) {
    try {
      const savedCode = await problemLoader.loadSavedCode(problemId, this.currentLanguage);
      if (savedCode) {
        this.setCode(savedCode);
        return true;
      }
    } catch (error) {
      console.error('저장된 코드 로드 실패:', error);
    }
    return false;
  }

  async saveProblemCode(problemId) {
    try {
      await problemLoader.saveProblemCode(problemId, this.currentLanguage, this.getCode());
      return true;
    } catch (error) {
      console.error('코드 저장 실패:', error);
      return false;
    }
  }
}

// 전역 인스턴스 (app.js에서 초기화)
let codeEditor;
