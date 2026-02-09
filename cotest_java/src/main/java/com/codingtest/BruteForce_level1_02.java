package com.codingtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Brute-Force Level1 - 모의고사 (수포자 삼인방)
 *
 * =============================================================================
 * 1. 문제
 * =============================================================================
 * 수포자 3명이 정해진 패턴으로 문제를 찍습니다. 1번 문제부터 마지막 문제까지의 정답이
 * 순서대로 담긴 배열 answers가 주어질 때, 가장 많은 문제를 맞힌 사람을 배열에 담아
 * 오름차순으로 return 하세요.
 *
 * 찍기 패턴:
 * - 1번: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
 * - 2번: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
 * - 3번: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
 *
 * [제한사항]
 * - 시험 문제 수(answers 길이)는 최대 10,000.
 * - 정답은 1, 2, 3, 4, 5 중 하나.
 * - 최고 점수가 동점이면 오름차순 정렬하여 return.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | answers       | return    |
 * |---------------|-----------|
 * | [1,2,3,4,5]   | [1]       |
 * | [1,3,2,4,2]   | [1,2,3]   |
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - 각 수포자의 "찍기 패턴"을 배열로 고정해 두고, i번째 문제에서는 pattern[i % pattern.length]와 정답을 비교하면 됨.
 * - 1번: 길이 5 (1,2,3,4,5), 2번: 길이 8 (2,1,2,3,2,4,2,5), 3번: 길이 10 (3,3,1,1,2,2,4,4,5,5).
 * - answers를 한 번 순회하면서, 각 수포자 패턴과 일치할 때마다 해당 수포자 카운트를 증가.
 * - 세 사람의 점수를 구한 뒤, 최댓값을 찾고, 최댓값과 같은 사람만 번호 순(1,2,3)으로 담아 return하면 자동으로 오름차순.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - 패턴 3개를 int[][] 또는 각각 int[]로 두고, 문제 인덱스 i에 대해 pattern[수포자][i % len] == answers[i]이면 맞힌 것.
 * - 세 수포자의 정답 수를 배열 또는 변수 3개로 세고, 한 번에 max를 구한 뒤, max와 같은 수포자 번호(1,2,3)를 리스트에 넣음.
 * - 번호를 1,2,3 순으로 보므로 리스트에 넣는 순서만 맞추면 별도 정렬 불필요.
 * - answers 길이 N, 패턴 길이 상수 → 시간 O(N), 공간 O(1).
 */
public class BruteForce_level1_02 {

    private static final int[] PATTERN_1 = {1, 2, 3, 4, 5};
    private static final int[] PATTERN_2 = {2, 1, 2, 3, 2, 4, 2, 5};
    private static final int[] PATTERN_3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

    public int[] solution(int[] answers) {
        int c1 = 0, c2 = 0, c3 = 0;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == PATTERN_1[i % PATTERN_1.length]) c1++;
            if (answers[i] == PATTERN_2[i % PATTERN_2.length]) c2++;
            if (answers[i] == PATTERN_3[i % PATTERN_3.length]) c3++;
        }

        int max = Math.max(c1, Math.max(c2, c3));
        List<Integer> list = new ArrayList<>();
        if (c1 == max) list.add(1);
        if (c2 == max) list.add(2);
        if (c3 == max) list.add(3);

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] solution2(int[] answers) {
        int[][] patterns = {
                {1, 2, 3, 4, 5},
                {2, 1, 2, 3, 2, 4, 2, 5},
                {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
        };

        // 각 수포자의 정답 수를 저장
        int[] hit = new int[3];
        for(int i = 0; i < hit.length; i++) {
            for(int j = 0; j < answers.length; j++) {
                if(patterns[i][j % patterns[i].length] == answers[j]) hit[i]++;
            }
        }

        // 최대 정답 수를 찾음
        int max = Math.max(hit[0], Math.max(hit[1], hit[2]));
        List<Integer> list = new ArrayList<>();

        // 최대 정답 수와 같은 수포자를 찾음
        for(int i = 0; i < hit.length; i++)
            if(max == hit[i]) list.add(i + 1);

        int[] answer = new int[list.size()];
        int cnt = 0;
        for(int num : list)
            answer[cnt++] = num;
        return answer;
    }
}
