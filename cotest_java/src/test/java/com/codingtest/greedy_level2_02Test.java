package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class greedy_level2_02Test {

    private final greedy_level2_02 solution = new greedy_level2_02();

    @Test
    @DisplayName("예제 1: 1924에서 2개 제거 → 94")
    void example1() {
        assertEquals("94", solution.solution("1924", 2));
    }

    @Test
    @DisplayName("예제 2: 1231234에서 3개 제거 → 3234")
    void example2() {
        assertEquals("3234", solution.solution("1231234", 3));
    }

    @Test
    @DisplayName("예제 3: 4177252841에서 4개 제거 → 775841")
    void example3() {
        assertEquals("775841", solution.solution("4177252841", 4));
    }
}
