package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class greedy_level2_01Test {

    private final greedy_level2_01 solution = new greedy_level2_01();

    @Test
    @DisplayName("예제 1: JEROEN → 56")
    void example1() {
        assertEquals(56, solution.solution("JEROEN"));
    }

    @Test
    @DisplayName("예제 2: JAN → 23")
    void example2() {
        assertEquals(23, solution.solution("JAN"));
    }

    @Test
    @DisplayName("JAZ → 11")
    void jaz() {
        assertEquals(11, solution.solution("JAZ"));
    }
}
