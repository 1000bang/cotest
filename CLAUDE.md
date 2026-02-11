# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

---

# 코딩 테스트 연습 저장소 (Coding Test Practice Repository)

이 저장소는 코딩 인터뷰 준비를 위한 개인 학습 프로젝트입니다. Java로 알고리즘 문제를 풀고, Python으로 자료구조를 학습합니다.

## 빌드 및 테스트 명령어

### Java (Maven)

```bash
# 프로젝트 빌드
cd cotest_java
mvn clean compile

# 전체 테스트 실행
mvn test

# 특정 테스트 클래스 실행
mvn test -Dtest=BruteForce_level1_01Test

# 특정 테스트 메서드 실행
mvn test -Dtest=BruteForce_level1_01Test#testSolution

# 빌드 및 패키징
mvn clean package
```

### Python

```bash
# Python 스크립트 직접 실행
cd cotest_py
python3 week2/linkedlist.py

# 특정 문제 실행
python3 week2/linkedlist_1.py
```

## 코드 구조 및 아키텍처

### Java 섹션 (`cotest_java/`)

**디렉토리 구조:**
```
cotest_java/
├── src/
│   ├── main/java/com/codingtest/    # 솔루션 구현
│   └── test/java/com/codingtest/    # JUnit 테스트
├── docs/
│   └── 주말복습_전체.md              # 학습 가이드
└── pom.xml                          # Maven 설정
```

**네이밍 규칙:**
- 파일명: `{카테고리}_level{1|2}_{번호}.java`
- 예시: `BruteForce_level1_01.java`, `hash_level2_02.java`
- 테스트: 파일명 + `Test` (예: `BruteForce_level1_01Test.java`)

**알고리즘 카테고리:**
- **BruteForce**: 완전 탐색, 순열, 소수 찾기
- **hash**: HashMap을 이용한 문제 (완주하지 못한 선수, 폰켓몬, 전화번호)
- **heap**: PriorityQueue 활용 (더 맵게, 디스크 컨트롤러)
- **stack**: 스택 자료구조 (중복 제거, 올바른 괄호)
- **queue**: 큐 자료구조 (기능개발)
- **sort**: 정렬 알고리즘 (K번째 수, 가장 큰 수)
- **greedy**: 탐욕 알고리즘

**각 문제 클래스 구조:**
1. 클래스 상단에 상세한 문제 설명 (한국어 주석)
2. 입출력 예시 및 제약조건
3. `solution()` 메서드로 알고리즘 구현
4. 필요시 `solution2()`, `solution3()` 등 여러 접근법 제공
5. 대응하는 테스트 클래스에 `@Test` 및 `@DisplayName` 사용

**예시:**
```java
// BruteForce_level1_01.java
/**
 * 문제: 명함 지갑 만들기
 * 모든 명함을 수납할 수 있는 최소 크기의 지갑
 * 명함은 회전 가능 (가로세로 바꿀 수 있음)
 */
public class BruteForce_level1_01 {
    public int solution(int[][] sizes) {
        // 구현...
    }
}
```

### Python 섹션 (`cotest_py/`)

**디렉토리 구조:**
```
cotest_py/
├── week1/    # 1주차 학습
└── week2/    # 2주차 학습 (링크드 리스트)
    ├── linkedlist.py      # 링크드 리스트 기초 구현
    └── linkedlist_1.py    # 링크드 리스트 합 문제
```

**특징:**
- 주차별로 자료구조 학습
- 공식 테스트 프레임워크 없음 (직접 실행으로 학습)
- 기본 자료구조 구현 및 연습 문제 포함
- Node 클래스와 LinkedList 클래스 등 기초부터 구현

## 학습 워크플로우

이 저장소는 체계적인 주말 학습 방식을 따릅니다 (`cotest_java/docs/주말복습_전체.md` 참고):

1. **토요일**: 문제 설명만 보고 스스로 풀어보기
2. **일요일**: 정답 코드와 비교하며 복습하기

**주요 학습 문제 (12개):**
- A1-A3: Brute Force (명함 지갑, 수포자, 소수 찾기)
- B1-B4: Hash (완주하지 못한 선수, 폰켓몬, 전화번호, 의상)
- C1-C2: Heap (더 맵게, 디스크 컨트롤러)
- D1: Queue (기능개발)
- E1-E2: Sort (K번째 수, 가장 큰 수)
- F1-F2: Stack (중복 제거, 올바른 괄호)

## 주요 스펙

- **Java 버전**: 17
- **JUnit 버전**: 5.10.0 (JUnit Jupiter)
- **Maven Compiler Plugin**: 3.11.0
- **Maven Surefire Plugin**: 3.1.2
- **인코딩**: UTF-8

## 테스트 작성 패턴

모든 Java 솔루션은 JUnit 5 테스트로 검증됩니다:

```java
@Test
@DisplayName("명함 지갑 크기 계산")
void testSolution() {
    BruteForce_level1_01 solver = new BruteForce_level1_01();
    int[][] sizes = {{60, 50}, {30, 70}, {60, 30}, {80, 40}};
    assertEquals(4000, solver.solution(sizes));
}
```

## Git 작업 흐름

- 주요 브랜치: `main`
- 커밋 메시지 스타일: 한국어로 간단명료하게 (예: "greedy", "python 추가 + study1 linkedlist")
- 카테고리별로 학습 후 커밋

## 문서

- `cotest_java/docs/주말복습_전체.md`: 12개 핵심 문제에 대한 상세 학습 가이드
  - 문제 설명
  - 힌트 (막힐 때 참고)
  - 정답 코드 위치
  - 각 카테고리별 접근 방법
