package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dfs_level2_01Test {

    private final dfs_level2_01 solution = new dfs_level2_01();

    @Test
    @DisplayName("예제 1: [1,1,1,1,1], target=3 → 5")
    void example1() {
        int[] numbers = {1, 1, 1, 1, 1};
        assertEquals(5, solution.solution(numbers, 3));
    }

    @Test
    @DisplayName("예제 2: [4,1,2,1], target=4 → 2")
    void example2() {
        int[] numbers = {4, 1, 2, 1};
        assertEquals(2, solution.solution(numbers, 4));
    }

    @Test
    @DisplayName("solution2 - 예제 1: [1,1,1,1,1], target=3 → 5")
    void solution2_example1() {
        int[] numbers = {1, 1, 1, 1, 1};
        assertEquals(5, solution.solution2(numbers, 3));
    }

    @Test
    @DisplayName("solution2 - 예제 2: [4,1,2,1], target=4 → 2")
    void solution2_example2() {
        int[] numbers = {4, 1, 2, 1};
        assertEquals(2, solution.solution2(numbers, 4));
    }
}
