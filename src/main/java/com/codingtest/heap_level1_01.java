package com.codingtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * =============================================================================
 * 1. 문제 (더 맵게)
 * =============================================================================
 * 매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다.
 * 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은 두 개의 음식을
 * 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.
 *
 *   섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
 *
 * Leo는 모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
 * Leo가 가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때,
 * 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.
 *
 * [제한사항]
 * - scoville의 길이는 2 이상 1,000,000 이하입니다.
 * - K는 0 이상 1,000,000,000 이하입니다.
 * - scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
 * - 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.
 *
 * =============================================================================
 * 2. 예시 입·출력
 * =============================================================================
 * | scoville                | K  | return |
 * |-------------------------|----|--------|
 * | [1, 2, 3, 9, 10, 12]    | 7  | 2      |
 *
 * 1회: 1 + (2*2) = 5 → [5, 3, 9, 10, 12]
 * 2회: 3 + (5*2) = 13 → [13, 9, 10, 12] → 모두 K(7) 이상
 *
 * =============================================================================
 * 3. 방향을 어떻게 잡아야 할지 고민
 * =============================================================================
 * - 매번 "가장 작은 두 개"를 꺼내서 섞고, 결과를 다시 넣어야 함. → 정렬된 것에서 최솟값 두 개가 필요.
 * - 배열을 매번 정렬하면 O(n log n)을 반복해서 비효율적. → 최소 힙(Heap)을 쓰면 루트가 항상 최솟값.
 * - PriorityQueue는 기본이 최소 힙. poll() 두 번으로 가장 작은 두 개를 꺼냄.
 * - 섞은 결과를 offer()로 넣고, 루트(최솟값)가 K 이상이 될 때까지 반복.
 * - 원소가 1개 남았는데도 K 미만이면 더 이상 섞을 수 없음 → -1.
 *
 * =============================================================================
 * 4. 설명 (왜 이렇게 코드를 짰는지)
 * =============================================================================
 * - PriorityQueue(최소 힙)에 scoville 전부 넣음. 매번 poll()로 가장 작은 것, 그다음 poll()로 두 번째로 작은 것 꺼냄.
 * - 섞은 값 = first + (second * 2)를 offer()로 다시 넣고, answer(섞은 횟수) +1.
 * - 루트(peek())가 K 이상이면 종료. 원소가 1개인데 K 미만이면 -1 반환.
 * - 배열 순서대로 섞으면 안 됨: 1회 후 [5, 3, 9, 10, 12]에서 "가장 작은 두 개"는 3과 5이지, 배열의 5와 3이 아님.
 */
public class heap_level1_01 {

    /**
     * PriorityQueue 없이 List + Collections.sort 로 구현.
     * - 매번 정렬 후 가장 작은 두 개(인덱스 0, 1)를 꺼내서 섞고, 그 두 개는 제거하고 섞은 값 하나만 추가.
     * - "두 개 제거 + 하나 추가"를 List.remove(0) 두 번 + add(섞은값)으로 처리.
     */
    public int solution(int[] scoville, int K) {
        List<Integer> list = new ArrayList<>();
        for (int s : scoville) {
            list.add(s);
        }

        int answer = 0;
        while (list.size() >= 2) {
            Collections.sort(list);
            if (list.get(0) >= K) {
                break;
            }
            int first = list.remove(0);
            int second = list.remove(0);
            list.add(first + (second * 2));
            answer++;
        }

        if (list.get(0) < K) {
            return -1;
        }
        return answer;
    }

    public int solution2(int[] scoville, int K) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int s : scoville) {
            heap.offer(s);
        }

        int answer = 0;
        while (heap.size() > 1 && heap.peek() < K) {
            int first = heap.poll();
            int second = heap.poll();
            heap.offer(first + (second * 2));
            answer++;
        }

        return heap.peek() < K ? -1 : answer;
    }
}
