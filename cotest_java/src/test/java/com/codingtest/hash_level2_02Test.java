package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class hash_level2_02Test {

    private final hash_level2_02 solution = new hash_level2_02();

    @Test
    @DisplayName("예제 1: headgear 2종, eyewear 1종 → 5")
    void example1() {
        String[][] clothes = {
                {"yellow_hat", "headgear"},
                {"blue_sunglasses", "eyewear"},
                {"green_turban", "headgear"}
        };
        assertEquals(5, solution.solution(clothes));
    }

    @Test
    @DisplayName("예제 2: face 3종 → 3")
    void example2() {
        String[][] clothes = {
                {"crow_mask", "face"},
                {"blue_sunglasses", "face"},
                {"smoky_makeup", "face"}
        };
        assertEquals(3, solution.solution(clothes));
    }
}
