package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class heap_level2_01Test {

    private final heap_level2_01 solution = new heap_level2_01();

    @Test
    @DisplayName("예제: [[0,3],[1,9],[3,5]] → 8")
    void example() {
        assertEquals(8, solution.solution(new int[][]{{0, 3}, {1, 9}, {3, 5}}));
    }
}
