package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForce_level1_02Test {

    private final BruteForce_level1_02 solution = new BruteForce_level1_02();

    @Test
    @DisplayName("예제 1: 1번만 전부 맞힘 → [1]")
    void example1() {
        int[] answers = {1, 2, 3, 4, 5};
        assertArrayEquals(new int[]{1}, solution.solution(answers));
    }

    @Test
    @DisplayName("예제 2: 세 명 동점 2문제 → [1, 2, 3]")
    void example2() {
        int[] answers = {1, 3, 2, 4, 2};
        assertArrayEquals(new int[]{1, 2, 3}, solution.solution(answers));
    }
}
