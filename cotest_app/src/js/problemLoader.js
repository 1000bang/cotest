const { ipcRenderer } = require('electron');

class ProblemLoader {
  constructor() {
    this.problems = [];
    this.progress = {};
    this.currentProblem = null;
  }

  async loadProblems() {
    try {
      // problems.json에서 문제 목록 로드
      this.problems = await ipcRenderer.invoke('load-problems');

      // 진행도 로드
      this.progress = await ipcRenderer.invoke('load-progress');

      return this.problems;
    } catch (error) {
      console.error('문제 로딩 실패:', error);
      return [];
    }
  }

  async loadProblemDetail(problemId) {
    try {
      const problem = this.problems.find(p => p.id === problemId);
      if (!problem) return null;

      // IPC를 통해 Java 파일 읽기
      const result = await ipcRenderer.invoke('read-java-file', problemId);

      if (!result.success) {
        console.error('Java 파일 읽기 실패:', result.error);
        return null;
      }

      const content = result.content;

      // 주석에서 문제 설명 추출
      const description = this.parseJavaComments(content);

      // 템플릿 코드 생성
      const templateJava = this.generateJavaTemplate(problemId, content);
      const templatePython = this.generatePythonTemplate(problemId);

      this.currentProblem = {
        ...problem,
        description,
        templateJava,
        templatePython
      };

      return this.currentProblem;
    } catch (error) {
      console.error('문제 상세 로딩 실패:', error);
      return null;
    }
  }

  parseJavaComments(content) {
    // /** ... */ 형식의 주석 추출
    const commentMatch = content.match(/\/\*\*([\s\S]*?)\*\//);
    if (!commentMatch) return '문제 설명을 찾을 수 없습니다.';

    let comment = commentMatch[1];

    // 각 줄의 앞 * 제거
    comment = comment.split('\n')
      .map(line => line.replace(/^\s*\*\s?/, ''))
      .join('\n')
      .trim();

    // "1. 문제"와 "2. 예시 입출력" 섹션만 추출
    const problemSection = this.extractSection(comment, '1. 문제', '2. 예시');
    const exampleSection = this.extractSection(comment, '2. 예시 입·출력', '3.');

    let result = '';
    if (problemSection) {
      result += '## 📋 문제\n\n' + problemSection + '\n\n';
    }
    if (exampleSection) {
      result += '## 📊 예시 입·출력\n\n' + exampleSection;
    }

    return result || comment;
  }

  extractSection(text, startMarker, endMarker) {
    const startIndex = text.indexOf(startMarker);
    if (startIndex === -1) return null;

    const contentStart = startIndex + startMarker.length;
    let endIndex = text.indexOf(endMarker, contentStart);

    if (endIndex === -1) {
      endIndex = text.length;
    }

    const section = text.substring(contentStart, endIndex).trim();

    // = 구분선 제거
    return section
      .split('\n')
      .filter(line => !line.match(/^=+$/))
      .join('\n')
      .trim();
  }

  generateJavaTemplate(problemId, content) {
    // import 문 추출
    const imports = [];
    const importRegex = /^import\s+.+;$/gm;
    let importMatch;
    while ((importMatch = importRegex.exec(content)) !== null) {
      imports.push(importMatch[0]);
    }

    // 기존 solution 메서드 시그니처 찾기
    const methodMatch = content.match(/public\s+[\w<>\[\]]+\s+solution\s*\([^)]*\)/);
    const returnType = methodMatch
      ? methodMatch[0].match(/public\s+([\w<>\[\]]+)\s+solution/)[1]
      : 'void';

    const methodSig = methodMatch
      ? methodMatch[0]
      : 'public void solution()';

    // 기본 반환값
    let defaultReturn = '';
    if (returnType === 'int') defaultReturn = '\n        return 0;';
    else if (returnType === 'int[]') defaultReturn = '\n        return new int[]{};';
    else if (returnType === 'String') defaultReturn = '\n        return "";';
    else if (returnType === 'boolean') defaultReturn = '\n        return false;';
    else if (returnType !== 'void') defaultReturn = '\n        return null;';

    const importStr = imports.length > 0 ? imports.join('\n') + '\n\n' : '';

    return `package com.codingtest;\n\n${importStr}public class ${problemId} {\n\n    ${methodSig} {\n        // 여기에 코드 작성\n${defaultReturn}\n    }\n}`;
  }

  generatePythonTemplate(problemId) {
    return `def solution(*args):\n    # 여기에 코드 작성\n    pass\n\n# 테스트\nif __name__ == "__main__":\n    result = solution()\n    print(result)`;
  }

  async saveProblemCode(problemId, language, code) {
    try {
      const key = `code_${problemId}_${language}`;
      localStorage.setItem(key, code);
      return true;
    } catch (error) {
      console.error('코드 저장 실패:', error);
      return false;
    }
  }

  async loadSavedCode(problemId, language) {
    try {
      const key = `code_${problemId}_${language}`;
      const code = localStorage.getItem(key);
      return code;
    } catch (error) {
      // 저장된 코드가 없으면 null 반환
      return null;
    }
  }

  async saveProgress(problemId, data) {
    try {
      await ipcRenderer.invoke('save-progress', {
        id: problemId,
        ...data
      });

      // 로컬 progress 업데이트
      this.progress[problemId] = data;
    } catch (error) {
      console.error('진행도 저장 실패:', error);
    }
  }

  isProblemSolved(problemId) {
    return this.progress[problemId]?.solved || false;
  }

  getCategoryProblems(category) {
    return this.problems.filter(p => p.category === category);
  }

  getAllCategories() {
    const categories = [...new Set(this.problems.map(p => p.category))];
    return categories.sort();
  }
}

// 전역 인스턴스
const problemLoader = new ProblemLoader();
