package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForce_level2_01Test {

    private final BruteForce_level2_01 solution = new BruteForce_level2_01();

    @Test
    @DisplayName("예제 1: 1,7로 소수 7, 17, 71 → 3개")
    void example1() {
        assertEquals(3, solution.solution("17"));
    }

    @Test
    @DisplayName("예제 2: 0,1,1로 소수 11, 101 → 2개")
    void example2() {
        assertEquals(2, solution.solution("011"));
    }
}
