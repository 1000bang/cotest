package com.codingtest;

import java.util.Arrays;

/**
 * =============================================================================
 * 1. 문제 (가장 큰 수)
 * =============================================================================
 * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 구합니다.
 *
 * 예: [6, 10, 2] → 6210, 6102, 2610, ... 중 가장 큰 수는 6210
 *
 * 0 또는 양의 정수가 담긴 배열 numbers가 주어질 때,
 * 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 return 하세요.
 *
 * [제한사항]
 * - numbers의 길이: 1 이상 100,000 이하
 * - numbers의 원소: 0 이상 1,000 이하
 * - 정답은 문자열로 return (숫자가 매우 클 수 있음)
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | numbers             | return     |
 * |---------------------|------------|
 * | [6, 10, 2]          | "6210"     |
 * | [3, 30, 34, 5, 9]   | "9534330"  |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "가장 큰 수" = 앞자리가 클수록 좋음. 단순 정렬(9, 8, 7...)이 안 되는 이유는 자릿수가 다르기 때문.
 *   예: 3 vs 30 → "330" vs "303" → 330이 더 크므로 3이 30보다 앞에 와야 함.
 * - 두 수 a, b를 "어떤 순서로 붙일지"만 정하면 됨: "a"+"b" vs "b"+"a" 를 문자열로 비교해서,
 *   더 큰 쪽이 나오는 순서로 정렬. (내림차순이 되게 하려면 (b+a).compareTo(a+b))
 * - 정렬 후 순서대로 이어 붙이면 됨. 단, 전부 0이면 "0" 한 개만 반환 (예: [0,0,0] → "0").
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - numbers를 문자열 배열로 바꾼 뒤, (a,b) -> (b+a).compareTo(a+b) 로 정렬하면
 *   "이어 붙였을 때 더 큰 조합"이 앞에 오는 순서가 됨.
 * - StringBuilder로 이어 붙이고, 맨 앞이 "0"이면(전부 0) "0" 반환.
 */
public class sort_level2_01 {

    public String solution(int[] numbers) {
        String[] arr = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            arr[i] = String.valueOf(numbers[i]);
        }

        // a, b 순서로 붙였을 때 "더 큰 문자열"이 나오는 쪽이 앞에 오도록 정렬 (내림차순)
        Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));

        if ("0".equals(arr[0])) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * "자릿수 비교"로 정렬.
     * - 두 수 a, b를 이어 붙인 결과 "a+b" vs "b+a"를 **자릿수 하나씩** 비교.
     * - 첫째 자리(앞자리)부터 비교하고, 같으면 둘째 자리 비교 ... 한쪽이 짧으면 그 수를 반복해서 채워서 비교.
     * - 예: 3 vs 34 → "33..." vs "34..." 로 보면 둘째 자리에서 3 < 4 이므로 34가 앞에 오는 쪽이 더 큼.
     */
    public String solution2(int[] numbers) {
        String[] arr = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            arr[i] = String.valueOf(numbers[i]);
        }

        Arrays.sort(arr, (a, b) -> compareConcatenationOrder(a, b)); // (a+b)가 더 크면 a가 앞에

        if ("0".equals(arr[0])) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * "a를 b보다 앞에 두었을 때(a+b)가 더 크면" 음수 반환 (a가 앞에 오게).
     * 즉 (a+b) vs (b+a) 를 자릿수별로 비교. 한쪽이 짧으면 이어 붙인 길이만큼 비교 (실제로는 a+b 길이만큼).
     */
    private int compareConcatenationOrder(String a, String b) {
        int len = a.length() + b.length();
        for (int i = 0; i < len; i++) {
            // (a+b) 의 i번째 자리
            char fromAThenB = i < a.length() ? a.charAt(i) : b.charAt(i - a.length());
            // (b+a) 의 i번째 자리
            char fromBThenA = i < b.length() ? b.charAt(i) : a.charAt(i - b.length());
            if (fromAThenB != fromBThenA) {
                return fromBThenA - fromAThenB; // (a+b)가 크면 음수 → a 앞
            }
        }
        return 0;
    }
}
