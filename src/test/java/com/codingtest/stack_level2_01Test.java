package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class stack_level2_01Test {

    private final stack_level2_01 solution = new stack_level2_01();

    @Test
    @DisplayName("예제 1: ()() → true")
    void example1() {
        assertTrue(solution.solution("()()"));
    }

    @Test
    @DisplayName("예제 2: (())() → true")
    void example2() {
        assertTrue(solution.solution("(())()"));
    }

    @Test
    @DisplayName("예제 3: )()( → false")
    void example3() {
        assertFalse(solution.solution(")()("));
    }

    @Test
    @DisplayName("예제 4: (()( → false")
    void example4() {
        assertFalse(solution.solution("(()("));
    }
}
