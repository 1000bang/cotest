package com.codingtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * =============================================================================
 * 1. 문제 (같은 숫자는 싫어 - 연속 중복 제거)
 * =============================================================================
 * 배열 arr가 주어집니다. 배열 arr의 각 원소는 숫자 0부터 9까지로 이루어져 있습니다.
 * 이때, 배열 arr에서 연속적으로 나타나는 숫자는 하나만 남기고 전부 제거하려고 합니다.
 * 단, 제거된 후 남은 수들을 반환할 때는 배열 arr의 원소들의 순서를 유지해야 합니다.
 *
 * [제한사항]
 * - 배열 arr의 크기: 1,000,000 이하의 자연수
 * - 배열 arr의 원소의 크기: 0보다 크거나 같고 9보다 작거나 같은 정수
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | arr              | answer    |
 * |------------------|-----------|
 * | [1, 1, 3, 3, 0, 1, 1] | [1, 3, 0, 1] |
 * | [4, 4, 4, 3, 3]       | [4, 3]       |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - "연속적으로 나타나는 숫자"만 제거 → 인접한 두 원소가 같으면 뒤만 제거. (전체 중복 제거 아님)
 * - 순서 유지 필요 → HashSet은 순서를 보장하지 않으므로 사용 불가.
 * - 한 번 순회로 처리: "이전 원소와 다를 때만" 결과에 넣으면 됨. (첫 원소는 항상 넣음)
 * - Stack/List로 순서대로 쌓거나, "직전 값"만 변수로 들고 가며 판단해도 됨.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - List에 순서대로 넣되, i == 0 이거나 arr[i] != arr[i-1] 일 때만 add. (연속 중복 제거)
 * - List를 int[]로 옮겨서 반환. (toArray는 Integer[]만 되므로 수동 복사)
 * - 시간 O(n), 공간 O(n). Stack을 써서 peek()과 비교하는 방식도 동일한 아이디어.
 */
public class stack_level1_01 {

    public int[] solution(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i == 0 || arr[i] != arr[i - 1]) {
                list.add(arr[i]);
            }
        }
        int[] answer = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i);
        }
        return answer;
    }

    public Stack<Integer> solution2(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        for (int num : arr) {
            if (stack.isEmpty() || stack.peek() != num) {
                stack.push(num);
            }
        }
        return stack;
    }

     public int[] solution3(int []arr) {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        // 원소는 0 이상 9 이하의 정수라 했기 때문에 첫번째 원소는 10으로 초기화
        int preNum = 10;
        for(int num : arr) {
            if(preNum != num)
                tempList.add(num);
            preNum = num;
        }       
        int[] answer = new int[tempList.size()];
        for(int i=0; i<answer.length; i++) {
            answer[i] = tempList.get(i).intValue();
        }
        return answer;
    }
}
