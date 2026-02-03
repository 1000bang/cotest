package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class stack_level1_01Test {

    private final stack_level1_01 solution = new stack_level1_01();

    @Test
    @DisplayName("예제 1: [1,1,3,3,0,1,1] → [1,3,0,1]")
    void example1() {
        int[] arr = {1, 1, 3, 3, 0, 1, 1};
        int[] expected = {1, 3, 0, 1};
        assertArrayEquals(expected, solution.solution(arr));
    }

    @Test
    @DisplayName("예제 2: [4,4,4,3,3] → [4,3]")
    void example2() {
        int[] arr = {4, 4, 4, 3, 3};
        int[] expected = {4, 3};
        assertArrayEquals(expected, solution.solution(arr));
    }

    @Test
    @DisplayName("원소 하나: [7] → [7]")
    void singleElement() {
        int[] arr = {7};
        int[] expected = {7};
        assertArrayEquals(expected, solution.solution(arr));
    }

    @Test
    @DisplayName("연속 중복 없음: [1,2,3] → [1,2,3]")
    void noConsecutiveDuplicate() {
        int[] arr = {1, 2, 3};
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, solution.solution(arr));
    }
}
