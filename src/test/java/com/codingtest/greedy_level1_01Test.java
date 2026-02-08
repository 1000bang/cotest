package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class greedy_level1_01Test {

    private final greedy_level1_01 solution = new greedy_level1_01();

    @Test
    @DisplayName("예제 1: 5명 전원 수업 가능")
    void example1() {
        assertEquals(5, solution.solution(5, new int[]{2, 4}, new int[]{1, 3, 5}));
        assertEquals(5, solution.solution2(5, new int[]{2, 4}, new int[]{1, 3, 5}));
        assertEquals(5, solution.solution3(5, new int[]{2, 4}, new int[]{1, 3, 5}));
    }

    @Test
    @DisplayName("예제 2: 4명만 수업 가능")
    void example2() {
        assertEquals(4, solution.solution(5, new int[]{2, 4}, new int[]{3}));
        assertEquals(4, solution.solution2(5, new int[]{2, 4}, new int[]{3}));
        assertEquals(4, solution.solution3(5, new int[]{2, 4}, new int[]{3}));
    }

    @Test
    @DisplayName("예제 3: 2명만 수업 가능")
    void example3() {
        assertEquals(2, solution.solution(3, new int[]{3}, new int[]{1}));
        assertEquals(2, solution.solution2(3, new int[]{3}, new int[]{1}));
        assertEquals(2, solution.solution3(3, new int[]{3}, new int[]{1}));
    }
}
