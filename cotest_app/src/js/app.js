// 간단 마크다운 → HTML 변환
function simpleMarkdown(text) {
  if (!text) return '';

  const lines = text.split('\n');
  const result = [];
  let i = 0;

  while (i < lines.length) {
    const line = lines[i];

    // 테이블 감지: | 로 시작하는 줄
    if (line.trim().startsWith('|') && line.trim().endsWith('|')) {
      // 테이블 행 수집
      const tableRows = [];
      while (i < lines.length && lines[i].trim().startsWith('|') && lines[i].trim().endsWith('|')) {
        const row = lines[i].trim();
        // 구분선(|---|---|) 건너뛰기
        if (!row.match(/^\|[\s\-:|]+$/)) {
          const cells = row.split('|').filter((_, idx, arr) => idx > 0 && idx < arr.length - 1).map(c => c.trim());
          tableRows.push(cells);
        }
        i++;
      }

      if (tableRows.length > 0) {
        let table = '<table>';
        // 첫 행은 헤더
        table += '<thead><tr>' + tableRows[0].map(c => `<th>${escapeHtml(c)}</th>`).join('') + '</tr></thead>';
        // 나머지 행은 바디
        if (tableRows.length > 1) {
          table += '<tbody>';
          for (let r = 1; r < tableRows.length; r++) {
            table += '<tr>' + tableRows[r].map(c => `<td>${escapeHtml(c)}</td>`).join('') + '</tr>';
          }
          table += '</tbody>';
        }
        table += '</table>';
        result.push(table);
      }
      continue;
    }

    // 헤더
    if (line.match(/^## /)) {
      result.push(`<h2>${escapeHtml(line.replace(/^## /, ''))}</h2>`);
      i++; continue;
    }
    if (line.match(/^### /)) {
      result.push(`<h3>${escapeHtml(line.replace(/^### /, ''))}</h3>`);
      i++; continue;
    }

    // 빈 줄
    if (line.trim() === '') {
      result.push('<br>');
      i++; continue;
    }

    // 일반 텍스트
    result.push(escapeHtml(line));
    i++;
  }

  return result.join('\n');
}

function escapeHtml(str) {
  return str
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
}

// 앱 초기화
document.addEventListener('DOMContentLoaded', async () => {
  console.log('코딩테스트 연습 앱 시작');

  // 코드 에디터 초기화
  codeEditor = new CodeEditor('code-textarea');

  // 문제 목록 로드
  await loadProblems();

  // 이벤트 리스너 설정
  setupEventListeners();
});

// 문제 목록 로드
async function loadProblems() {
  try {
    const problems = await problemLoader.loadProblems();

    if (problems.length === 0) {
      console.warn('문제 목록이 비어있습니다');
      return;
    }

    // 문제 목록 렌더링
    renderProblemList(problems);

    // 통계 업데이트
    updateStats(problems);
  } catch (error) {
    console.error('문제 로딩 실패:', error);
  }
}

// 문제 목록 렌더링
function renderProblemList(problems) {
  const problemListEl = document.getElementById('problem-list');
  problemListEl.innerHTML = '';

  // 카테고리별로 그룹화
  const categories = problemLoader.getAllCategories();

  categories.forEach(category => {
    const categoryProblems = problemLoader.getCategoryProblems(category);

    const categoryEl = document.createElement('div');
    categoryEl.className = 'problem-category';

    const titleEl = document.createElement('div');
    titleEl.className = 'category-title';
    titleEl.textContent = `${category} (${categoryProblems.length})`;

    categoryEl.appendChild(titleEl);

    categoryProblems.forEach(problem => {
      const problemEl = createProblemElement(problem);
      categoryEl.appendChild(problemEl);
    });

    problemListEl.appendChild(categoryEl);
  });
}

// 문제 요소 생성
function createProblemElement(problem) {
  const el = document.createElement('div');
  el.className = 'problem-item';
  el.dataset.problemId = problem.id;

  // 완료 여부 표시
  if (problemLoader.isProblemSolved(problem.id)) {
    el.classList.add('solved');
  }

  const titleEl = document.createElement('div');
  titleEl.className = 'problem-item-title';
  titleEl.textContent = problem.title;

  const metaEl = document.createElement('div');
  metaEl.className = 'problem-item-meta';
  metaEl.innerHTML = `
    <span class="badge level-${problem.level}">Level ${problem.level}</span>
  `;

  el.appendChild(titleEl);
  el.appendChild(metaEl);

  // 클릭 이벤트
  el.addEventListener('click', () => {
    selectProblem(problem.id);
  });

  return el;
}

// 문제 선택
async function selectProblem(problemId) {
  // 활성 상태 업데이트
  document.querySelectorAll('.problem-item').forEach(el => {
    el.classList.remove('active');
  });
  document.querySelector(`[data-problem-id="${problemId}"]`)?.classList.add('active');

  // 문제 상세 로드
  const problem = await problemLoader.loadProblemDetail(problemId);

  if (!problem) {
    alert('문제를 불러올 수 없습니다.');
    return;
  }

  // UI 업데이트
  document.getElementById('problem-title').textContent = problem.title;
  document.getElementById('problem-level').textContent = `Level ${problem.level}`;
  document.getElementById('problem-level').className = `badge level-${problem.level}`;
  document.getElementById('problem-category').textContent = problem.category;
  document.getElementById('problem-category').className = 'badge';

  // 문제 설명 (간단 마크다운 → HTML 변환)
  document.getElementById('problem-description').innerHTML = simpleMarkdown(problem.description);

  // 코드 에디터 초기화
  const language = document.getElementById('language-selector').value;
  const template = language === 'java' ? problem.templateJava : problem.templatePython;

  // 저장된 코드가 있으면 불러오기, 없으면 템플릿 사용
  const hasSavedCode = await codeEditor.loadProblemCode(problemId);
  if (!hasSavedCode) {
    codeEditor.setCode(template);
  }

  // 테스트 결과 초기화
  document.getElementById('test-output').textContent = '';
  document.getElementById('test-output').className = '';
}

// 테스트 실행
async function runTest() {
  const currentProblem = problemLoader.currentProblem;

  if (!currentProblem) {
    alert('문제를 선택해주세요.');
    return;
  }

  const code = codeEditor.getCode();
  const language = document.getElementById('language-selector').value;

  // 코드 저장
  await codeEditor.saveProblemCode(currentProblem.id);

  // 테스트 실행 버튼 비활성화
  const runBtn = document.getElementById('run-test-btn');
  runBtn.disabled = true;
  runBtn.textContent = '⏳ 실행 중...';

  // 결과 초기화
  const outputEl = document.getElementById('test-output');
  outputEl.textContent = '테스트를 실행하는 중입니다...\n';
  outputEl.className = '';

  try {
    let result;

    if (language === 'java') {
      result = await codeRunner.runJavaTest(currentProblem.id, code);
    } else {
      result = await codeRunner.runPythonCode(code);
    }

    // 결과 표시
    outputEl.textContent = result.message + '\n\n' + (result.output || '');
    outputEl.className = result.success ? 'success' : 'error';

    // 성공하면 진행도 저장
    if (result.success) {
      await problemLoader.saveProgress(currentProblem.id, {
        solved: true,
        language: language,
        lastAttempt: new Date().toISOString()
      });

      // 문제 목록 업데이트
      const problemEl = document.querySelector(`[data-problem-id="${currentProblem.id}"]`);
      if (problemEl) {
        problemEl.classList.add('solved');
      }

      // 통계 업데이트
      updateStats(problemLoader.problems);
    }
  } catch (error) {
    outputEl.textContent = '오류 발생:\n' + error.message;
    outputEl.className = 'error';
  } finally {
    // 버튼 활성화
    runBtn.disabled = false;
    runBtn.textContent = '▶ 테스트 실행';
  }
}

// 코드 초기화
function resetCode() {
  if (!confirm('코드를 초기화하시겠습니까? 저장되지 않은 내용은 사라집니다.')) {
    return;
  }

  const currentProblem = problemLoader.currentProblem;
  if (!currentProblem) return;

  const language = document.getElementById('language-selector').value;
  const template = language === 'java' ? currentProblem.templateJava : currentProblem.templatePython;

  codeEditor.setCode(template);
}

// 통계 업데이트
function updateStats(problems) {
  const total = problems.length;
  const solved = problems.filter(p => problemLoader.isProblemSolved(p.id)).length;

  document.getElementById('solved-count').textContent = solved;
  document.getElementById('total-count').textContent = total;
}

// 이벤트 리스너 설정
function setupEventListeners() {
  // 언어 선택
  document.getElementById('language-selector').addEventListener('change', (e) => {
    codeEditor.setLanguage(e.target.value);

    // 현재 문제의 템플릿으로 초기화
    const currentProblem = problemLoader.currentProblem;
    if (currentProblem) {
      const language = e.target.value;
      const template = language === 'java' ? currentProblem.templateJava : currentProblem.templatePython;

      // 저장된 코드 확인
      problemLoader.loadSavedCode(currentProblem.id, language).then(savedCode => {
        if (!savedCode) {
          codeEditor.setCode(template);
        }
      });
    }
  });

  // 테스트 실행
  document.getElementById('run-test-btn').addEventListener('click', runTest);

  // 코드 초기화
  document.getElementById('reset-code-btn').addEventListener('click', resetCode);
}
