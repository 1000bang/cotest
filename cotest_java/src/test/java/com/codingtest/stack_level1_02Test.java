package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class stack_level1_02Test {

    private final stack_level1_02 solution = new stack_level1_02();

    @Test
    @DisplayName("예제 1: [93,30,55], [1,30,5] → [2,1]")
    void example1() {
        int[] progresses = {93, 30, 55};
        int[] speeds = {1, 30, 5};
        int[] expected = {2, 1};
        assertArrayEquals(expected, solution.solution(progresses, speeds));
    }

    @Test
    @DisplayName("예제 2: [95,90,99,99,80,99], [1,1,1,1,1,1] → [1,3,2]")
    void example2() {
        int[] progresses = {95, 90, 99, 99, 80, 99};
        int[] speeds = {1, 1, 1, 1, 1, 1};
        int[] expected = {1, 3, 2};
        assertArrayEquals(expected, solution.solution(progresses, speeds));
    }
}
