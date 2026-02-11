package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class greedy_level2_03Test {

    private final greedy_level2_03 solution = new greedy_level2_03();

    @Test
    @DisplayName("예제 1: [70,50,80,50] limit=100 → 3")
    void example1() {
        assertEquals(3, solution.solution(new int[]{70, 50, 80, 50}, 100));
    }

    @Test
    @DisplayName("예제 2: [70,80,50] limit=100 → 3")
    void example2() {
        assertEquals(3, solution.solution(new int[]{70, 80, 50}, 100));
    }
}
