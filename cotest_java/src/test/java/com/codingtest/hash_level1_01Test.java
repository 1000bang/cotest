package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class hash_level1_01Test {

    private final hash_level1_01 solution = new hash_level1_01();

    @Test
    @DisplayName("예제 1: leo 미완주")
    void example1() {
        String[] participant = {"leo", "kiki", "eden"};
        String[] completion = {"eden", "kiki"};
        assertEquals("leo", solution.solution(participant, completion));
    }

    @Test
    @DisplayName("예제 2: vinko 미완주")
    void example2() {
        String[] participant = {"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] completion = {"josipa", "filipa", "marina", "nikola"};
        assertEquals("vinko", solution.solution(participant, completion));
    }

    @Test
    @DisplayName("예제 3: 동명이인 mislav 한 명 미완주")
    void example3() {
        String[] participant = {"mislav", "stanko", "mislav", "ana"};
        String[] completion = {"stanko", "ana", "mislav"};
        assertEquals("mislav", solution.solution(participant, completion));
    }
}
