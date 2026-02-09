package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class hash_level1_02Test {

    private final hash_level1_02 solution = new hash_level1_02();

    @Test
    @DisplayName("예제 1: [3,1,2,3] → 2")
    void example1() {
        assertEquals(2, solution.solution(new int[]{3, 1, 2, 3}));
    }

    @Test
    @DisplayName("예제 2: [3,3,3,2,2,4] → 3")
    void example2() {
        assertEquals(3, solution.solution(new int[]{3, 3, 3, 2, 2, 4}));
    }

    @Test
    @DisplayName("예제 3: [3,3,3,2,2,2] → 2")
    void example3() {
        assertEquals(2, solution.solution(new int[]{3, 3, 3, 2, 2, 2}));
    }
}
