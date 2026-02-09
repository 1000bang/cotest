package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class heap_level1_01Test {

    private final heap_level1_01 solution = new heap_level1_01();

    @Test
    @DisplayName("예제: [1,2,3,9,10,12], K=7 → 2")
    void example() {
        assertEquals(2, solution.solution(new int[]{1, 2, 3, 9, 10, 12}, 7));
    }

    @Test
    @DisplayName("이미 모두 K 이상: [7,7], K=7 → 0")
    void alreadyAllAboveK() {
        assertEquals(0, solution.solution(new int[]{7, 7}, 7));
    }

    @Test
    @DisplayName("만들 수 없음: [1,1], K=7 → -1")
    void impossible() {
        assertEquals(-1, solution.solution(new int[]{1, 1}, 7));
    }

    @Test
    @DisplayName("여러 번 섞기: [1,1,1,1,1,10], K=5 → 4")
    void multipleMixes() {
        assertEquals(4, solution.solution(new int[]{1, 1, 1, 1, 1, 10}, 5));
    }
}
