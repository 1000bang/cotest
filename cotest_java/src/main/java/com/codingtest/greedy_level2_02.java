package com.codingtest;

import java.util.Stack;

/**
 * =============================================================================
 * 1. 문제 (큰 수 만들기)
 * =============================================================================
 * 어떤 숫자에서 k개의 수를 제거했을 때 얻을 수 있는 가장 큰 숫자를 구하려 합니다.
 *
 * 예를 들어, 숫자 1924에서 수 두 개를 제거하면
 * [19, 12, 14, 92, 94, 24] 를 만들 수 있습니다.
 * 이 중 가장 큰 숫자는 94 입니다.
 *
 * 문자열 형식으로 숫자 number와 제거할 수의 개수 k가
 * solution 함수의 매개변수로 주어집니다.
 * number에서 k개의 수를 제거했을 때 만들 수 있는 수 중
 * 가장 큰 숫자를 문자열 형태로 return 하도록 solution 함수를 완성하세요.
 *
 * [제한사항]
 * - number는 2자리 이상, 1,000,000자리 이하인 숫자입니다.
 * - k는 1 이상 number의 자릿수 미만인 자연수입니다.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | number       | k | return   |
 * |--------------|---|----------|
 * | "1924"       | 2 | "94"     |
 * | "1231234"    | 3 | "3234"   |
 * | "4177252841" | 4 | "775841" |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - 스택을 활용한 그리디 접근
 * - 앞에서부터 숫자를 하나씩 보면서, 스택의 top보다 현재 숫자가 크면
 *   스택에서 pop (= 그 숫자를 제거) → k번까지 반복
 * - 이렇게 하면 앞자리에 큰 숫자가 오게 되어 최대값을 만들 수 있음
 */
public class greedy_level2_02 {

    public String solution(String number, int k) {
        Stack<Character> stack = new Stack<>();
        // removedCount: 제거한 숫자의 개수
        int removedCount = 0;
        for (char digit : number.toCharArray()){
            // 현재 숫자가 스택의 top보다 크고, 아직 k개를 제거하지 않았다면
            while (!stack.isEmpty() && stack.peek() < digit && removedCount < k) {
                stack.pop();
                removedCount++;
            }
            stack.push(digit);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stack.size() - (k - removedCount); i++) {
            result.append(stack.get(i));
        }
        return result.toString();
    }

    String solution2 (String number, int k) {
        StringBuilder answer = new StringBuilder();
        int len = number.length() - k;
        int start = 0;

        for (int i = 0; i < len; i++) {
            char maxChar = '0';
            for (int j = start; j <= k + i; j++) {
                if (number.charAt(j) > maxChar) {
                    maxChar = number.charAt(j);
                    start = j + 1;
                }
            }
            answer.append(maxChar);
        }
        return answer.toString();
    }
}
