package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dp_level2_02Test {

    private final dp_level2_02 solution = new dp_level2_02();

    @Test
    @DisplayName("예제 1: [\"1\",\"-\",\"3\",\"+\",\"5\",\"-\",\"8\"] → 1")
    void example1() {
        String[] arr = {"1", "-", "3", "+", "5", "-", "8"};
        assertEquals(1, solution.solution(arr));
    }

    @Test
    @DisplayName("예제 2: [\"5\",\"-\",\"3\",\"+\",\"1\",\"+\",\"2\",\"-\",\"4\"] → 3")
    void example2() {
        String[] arr = {"5", "-", "3", "+", "1", "+", "2", "-", "4"};
        assertEquals(3, solution.solution(arr));
    }

    @Test
    @DisplayName("단순 덧셈: [\"1\",\"+\",\"2\",\"+\",\"3\"] → 6")
    void allPlus() {
        String[] arr = {"1", "+", "2", "+", "3"};
        assertEquals(6, solution.solution(arr));
    }

    @Test
    @DisplayName("숫자 2개: [\"10\",\"-\",\"5\"] → 5")
    void twoNumbers() {
        String[] arr = {"10", "-", "5"};
        assertEquals(5, solution.solution(arr));
    }
}
