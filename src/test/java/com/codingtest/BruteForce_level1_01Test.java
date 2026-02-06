package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForce_level1_01Test {

    private final BruteForce_level1_01 solution = new BruteForce_level1_01();

    @Test
    @DisplayName("예제 1: 80x50 지갑 = 4000")
    void example1() {
        int[][] sizes = {{60, 50}, {30, 70}, {60, 30}, {80, 40}};
        assertEquals(4000, solution.solution(sizes));
    }

    @Test
    @DisplayName("예제 2: 120")
    void example2() {
        int[][] sizes = {{10, 7}, {12, 3}, {8, 15}, {14, 7}, {5, 15}};
        assertEquals(120, solution.solution(sizes));
    }

    @Test
    @DisplayName("예제 3: 133")
    void example3() {
        int[][] sizes = {{14, 4}, {19, 6}, {6, 16}, {18, 7}, {7, 11}};
        assertEquals(133, solution.solution(sizes));
    }
}
