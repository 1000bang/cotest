package com.codingtest;

import java.util.*;


/**
 * Brute-Force Level2 - 소수 찾기
 *
 * =============================================================================
 * 1. 문제
 * =============================================================================
 * 한 자리 숫자가 적힌 종이 조각이 흩어져 있습니다. 흩어진 종이 조각을 붙여
 * 소수를 몇 개 만들 수 있는지 구합니다.
 *
 * 종이 조각에 적힌 숫자가 담긴 문자열 numbers가 주어질 때,
 * 종이 조각으로 만들 수 있는 소수의 개수를 return 하세요.
 *
 * [제한사항]
 * - numbers 길이는 1 이상 7 이하.
 * - numbers는 0~9 숫자만으로 이루어짐.
 * - "013"은 0, 1, 3이 적힌 조각이 있다는 의미 (각 조각은 최대 그 개수만큼 사용).
 * - 11과 011은 같은 숫자로 취급.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | numbers | return |
 * |---------|--------|
 * | "17"    | 3      |
 * | "011"   | 2      |
 *
 * 예: "17" → [7, 17, 71] 소수 3개. "011" → [11, 101] 소수 2개.
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - 만들 수 있는 "숫자" = 주어진 숫자 조각을 각각 최대 1번씩 사용해서 만든 순열(길이 1~n).
 * - 같은 숫자 조합이면 한 번만 세야 하므로 Set<Integer>로 중복 제거 (011 → 11).
 * - 순열 생성: 인덱스 기준으로 used[] 두고, 길이 1부터 n까지 백트래킹으로 이어 붙이기.
 *   이어 붙인 문자열을 Integer로 파싱하면 앞의 0은 자동으로 제거되므로 Set에 넣기만 하면 됨.
 * - 모든 후보를 Set에 모은 뒤, 2 이상인 수에 대해 소수 판별하여 개수만 세면 됨.
 * - 소수 판별: 2 미만은 제외, 2 이상이면 2 ~ sqrt(n) 사이에 약수가 있는지 확인.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - collect(used, path, numbers, set): 사용한 인덱스 used, 지금까지 이어 붙인 path.
 *   한 번이라도 이어 붙였으면 path를 정수로 파싱해 set에 넣고, 사용하지 않은 인덱스 하나씩 붙여가며 재귀.
 * - 이렇게 하면 길이 1, 2, ..., n 인 모든 순열이 생성되고, parseInt로 인해 01=1 등으로 합쳐져 Set에 유일하게 저장됨.
 * - 소수 개수: set을 순회하며 2 이상인 수만 isPrime()으로 판별해 카운트.
 * - isPrime(n): n < 2면 false, 2 이상이면 2 ~ sqrt(n)까지 나누어떨어지면 false.
 * - numbers 길이 7이하이므로 순열 개수가 많아도 수만 단위라 충분히 가능.
 */
public class BruteForce_level2_01 {

    /**
     * 종이 조각으로 만들 수 있는 소수의 개수를 반환한다.
     */
    public int solution(String numbers) {
        // 1단계: 조각을 이어 붙여 만들 수 있는 모든 숫자(중복 제거)를 수집
        Set<Integer> candidates = new HashSet<>();
        boolean[] used = new boolean[numbers.length()];  // i번째 조각 사용 여부
        collect(used, new StringBuilder(), numbers, candidates);

        // 2단계: 후보 중 2 이상이면서 소수인 것만 카운트 (1과 0은 소수 아님)
        int count = 0;
        for (int num : candidates) {
            if (num >= 2 && isPrime(num)) count++;
        }
        return count;
    }

    /**
     * 백트래킹으로 "현재까지 선택한 조각으로 만든 숫자"를 모두 만들어 out에 넣는다.
     * - used: 이미 사용한 numbers 인덱스
     * - path: 지금까지 이어 붙인 숫자 문자열 (예: "17")
     * - 길이 1, 2, ..., n 인 모든 순열이 생성되고, parseInt로 "01"→1 처리가 됨
     */
    private void collect(boolean[] used, StringBuilder path, String numbers, Set<Integer> out) {
        // 한 글자라도 붙였으면 그때의 숫자를 Set에 추가 (011과 11은 같은 수로 합쳐짐)
        if (path.length() > 0) {
            out.add(Integer.parseInt(path.toString()));
        }
        // 사용하지 않은 조각을 하나씩 붙여 보며 재귀
        for (int i = 0; i < numbers.length(); i++) {
            if (used[i]) continue;
            used[i] = true;
            path.append(numbers.charAt(i));
            collect(used, path, numbers, out);
            // 백트래킹: 방금 붙인 글자 제거 후 다음 경우로
            path.setLength(path.length() - 1);
            used[i] = false;
        }
    }

    /**
     * n이 소수인지 판별한다.
     * - 2 미만은 소수가 아님.
     * - 2 이상이면 2 ~ sqrt(n) 사이에 약수가 하나라도 있으면 소수가 아님.
     */
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


    //======================= solution2 (다른 사람 풀이 발췌) =======================

    /** 만들 수 있는 숫자 후보를 중복 제거·정렬하여 담는 집합 (011 → 11로 파싱되어 들어감) */
    TreeSet<Integer> ts = new TreeSet<>();

    /**
     * func로 모든 후보를 수집한 뒤, 2 이상인 수에 대해 소수 여부를 판별해 개수를 센다.
     */
    public int solution2(String numbers) {
        int answer = 0, i;
        func("", numbers);

        for (int num : ts) {
            // 2 ~ num-1 사이에 약수가 있으면 소수가 아님 (있으면 break로 i < num 상태로 끝남)
            for (i = 2; i < num; i++) {
                if (num % i == 0)
                    break;
            }
            // break 없이 루프가 끝나면 i == num 이 되어 소수로 카운트 (num < 2면 루프 안 돌아서 카운트 안 됨)
            if (i == num)
                answer++;
        }
        return answer;
    }

    /**
     * 재귀: 현재까지 만든 문자열 s, 아직 쓰지 않은 숫자들 number.
     * - number가 비었을 때: s가 비어 있지 않으면 정수로 파싱해 ts에 추가.
     * - 그렇지 않으면:
     *   1) 첫 번째 for: i번째 숫자를 "선택해서" s에 붙이고, 나머지 number로 재귀 → s가 점점 길어짐.
     *   2) 두 번째 for: i번째 숫자를 "건너뛰고", s는 그대로 두고 number에서만 제거 후 재귀 → 길이 0, 1, ..., n 인 모든 순열 생성.
     */
    public void func(String s, String number) {
        if (number.length() == 0) {
            if (!s.equals(""))
                ts.add(Integer.parseInt(s));
        } else {
            for (int i = 0; i < number.length(); i++)
                func(s + number.charAt(i), number.substring(0, i) + number.substring(i + 1, number.length()));
            for (int i = 0; i < number.length(); i++)
                func(s, number.substring(0, i) + number.substring(i + 1, number.length()));
        }
    }
}
