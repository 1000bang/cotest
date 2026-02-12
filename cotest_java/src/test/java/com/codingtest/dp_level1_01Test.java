package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dp_level1_01Test {

    private final dp_level1_01 solution = new dp_level1_01();

    @Test
    @DisplayName("예제 1: N=5, number=12 → 4")
    void example1() {
        assertEquals(4, solution.solution(5, 12));
    }

    @Test
    @DisplayName("예제 2: N=2, number=11 → 3")
    void example2() {
        assertEquals(3, solution.solution(2, 11));
    }
}
