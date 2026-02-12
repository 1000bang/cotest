package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dp_level1_02Test {

    private final dp_level1_02 solution = new dp_level1_02();

    @Test
    @DisplayName("예제 1: 정수 삼각형 → 30")
    void example1() {
        int[][] triangle = {
            {7},
            {3, 8},
            {8, 1, 0},
            {2, 7, 4, 4},
            {4, 5, 2, 6, 5}
        };
        assertEquals(30, solution.solution(triangle));
    }
}
