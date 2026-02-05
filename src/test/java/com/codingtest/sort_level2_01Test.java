package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class sort_level2_01Test {

    private final sort_level2_01 solution = new sort_level2_01();

    @Test
    @DisplayName("예제 1: [6, 10, 2] → 6210")
    void example1() {
        assertEquals("6210", solution.solution(new int[]{6, 10, 2}));
    }

    @Test
    @DisplayName("예제 2: [3, 30, 34, 5, 9] → 9534330")
    void example2() {
        assertEquals("9534330", solution.solution(new int[]{3, 30, 34, 5, 9}));
    }

    @Test
    @DisplayName("전부 0: [0, 0, 0] → 0")
    void allZeros() {
        assertEquals("0", solution.solution(new int[]{0, 0, 0}));
    }

    @Test
    @DisplayName("solution2(자릿수 비교): 예제 1, 2 동일 결과")
    void solution2() {
        assertEquals("6210", solution.solution2(new int[]{6, 10, 2}));
        assertEquals("9534330", solution.solution2(new int[]{3, 30, 34, 5, 9}));
        assertEquals("0", solution.solution2(new int[]{0, 0, 0}));
    }
}
