# 코딩테스트 연습 앱

오프라인에서 동작하는 코딩 테스트 연습 GUI 애플리케이션입니다.

## 🚀 빠른 시작

### 1. 앱 실행 (개발 모드)

```bash
cd /Users/1000bang/cotest/cotest_app
npm start
```

앱이 실행되면 다음을 볼 수 있습니다:
- 왼쪽: 16개 문제 목록 (카테고리별로 정리)
- 오른쪽: 문제 상세, 코드 에디터, 힌트, 해설

### 2. 문제 풀기

1. **문제 선택**: 왼쪽 사이드바에서 문제 클릭
2. **문제 읽기**: "📖 문제" 탭에서 문제 설명 확인
3. **코드 작성**: "💻 코드" 탭으로 이동하여 코드 작성
4. **테스트 실행**: "▶ 테스트 실행" 버튼 클릭
5. **결과 확인**:
   - ✅ 초록색 = 통과
   - ❌ 빨간색 = 실패

### 3. 언어 선택

- Java (기본)
- Python

코드 에디터 상단의 드롭다운에서 선택할 수 있습니다.

### 4. 힌트/해설

- **💡 힌트**: 막힐 때 참고 (개발 예정)
- **✅ 해설**: 풀이 후 확인 (개발 예정)

## 📦 Mac 실행 파일 만들기

```bash
cd /Users/1000bang/cotest/cotest_app
npm run build:mac
```

완료되면 `dist/` 폴더에 `.dmg` 파일이 생성됩니다:
- 파일: `cotest_app/dist/코딩테스트 연습-1.0.0.dmg`
- 더블클릭으로 설치 가능
- 설치 후 오프라인에서도 동작

## 📁 프로젝트 구조

```
cotest_app/
├── electron.js                  # Electron 메인 프로세스
├── package.json                 # npm 설정
├── generate-problems.js         # 문제 목록 생성 스크립트
├── src/
│   ├── index.html              # 메인 화면
│   ├── css/style.css           # 스타일
│   ├── js/
│   │   ├── app.js             # 앱 메인 로직
│   │   ├── problemLoader.js   # 문제 로딩
│   │   ├── codeRunner.js      # 코드 실행
│   │   └── editor.js          # 에디터 관리
│   └── data/
│       ├── problems.json      # 문제 메타데이터 (자동 생성)
│       └── progress.json      # 학습 진행도 (자동 생성)
└── saved_codes/               # 저장된 코드 (자동 생성)
```

## 🎯 주요 기능

### ✅ 구현 완료
- [x] 문제 목록 (카테고리별, 난이도별)
- [x] 문제 상세 보기 (설명, 예시, 제약조건)
- [x] 코드 에디터 (Java/Python)
- [x] 자동 테스트 실행 (Java: Maven, Python: 직접 실행)
- [x] 테스트 결과 표시 (통과/실패)
- [x] 학습 진행도 추적 (완료한 문제 표시)
- [x] 코드 자동 저장 (문제별, 언어별)

### 🔜 개발 예정
- [ ] 힌트 기능 (주말복습 문서 연동)
- [ ] 해설 기능 (정답 코드 보기)
- [ ] 대시보드 (통계, 최근 푼 문제)
- [ ] Monaco Editor 통합 (더 나은 코드 에디터)

## 🛠️ 기술 스택

- **Electron**: 데스크톱 앱 프레임워크
- **Node.js**: 백엔드 로직
- **HTML/CSS/JavaScript**: 프론트엔드
- **Maven**: Java 테스트 실행
- **Python**: Python 코드 실행

## 📝 문제 추가 방법

1. `cotest_java/src/main/java/com/codingtest/`에 새 문제 추가
2. 테스트 파일 작성 (`src/test/java/com/codingtest/`)
3. 문제 목록 재생성:
   ```bash
   cd cotest_app
   node generate-problems.js
   ```

## 🐛 문제 해결

### 앱이 실행되지 않음
```bash
# node_modules 재설치
rm -rf node_modules package-lock.json
npm install
```

### Java 테스트가 실행되지 않음
```bash
# Maven 확인
cd cotest_java
mvn clean test
```

### Python 코드가 실행되지 않음
```bash
# Python 버전 확인
python3 --version
```

## 📚 사용 팁

1. **코드 자동 저장**: 3초마다 자동으로 저장됩니다
2. **언어 전환**: 각 언어별로 코드가 따로 저장됩니다
3. **진행도**: 테스트를 통과하면 자동으로 완료 표시됩니다
4. **문제 초기화**: "↺ 코드 초기화" 버튼으로 템플릿으로 돌아갈 수 있습니다

## 🎓 학습 전략

1. **초급 (1-2주)**: Level 1 문제부터 시작
   - BruteForce, Hash, Stack, Queue

2. **중급 (3-4주)**: Level 2 문제 도전
   - Sort, Heap, Greedy

3. **복습**: 주말에 틀린 문제 다시 풀기
   - 주말복습 문서 참고 (`cotest_java/docs/주말복습_전체.md`)

## 📞 문의

문제가 있거나 기능 제안이 있으시면 이슈를 등록해주세요!
