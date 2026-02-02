package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class hash_level2_01Test {

    private final hash_level2_01 solution = new hash_level2_01();

    @Test
    @DisplayName("예제 1: 119가 1195524421의 접두어 → false")
    void example1() {
        assertFalse(solution.solution(new String[]{"119", "97674223", "1195524421"}));
    }

    @Test
    @DisplayName("예제 2: 접두어 없음 → true")
    void example2() {
        assertTrue(solution.solution(new String[]{"123", "456", "789"}));
    }

    @Test
    @DisplayName("예제 3: 12가 123, 1235의 접두어 → false")
    void example3() {
        assertFalse(solution.solution(new String[]{"12", "123", "1235", "567", "88"}));
    }
}
