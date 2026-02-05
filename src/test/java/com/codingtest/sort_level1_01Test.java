package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class sort_level1_01Test {

    private final sort_level1_01 solution = new sort_level1_01();

    @Test
    @DisplayName("예제: [1,5,2,6,3,7,4], [[2,5,3],[4,4,1],[1,7,3]] → [5,6,3]")
    void example() {
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};
        int[] expected = {5, 6, 3};
        assertArrayEquals(expected, solution.solution(array, commands));
    }
}
