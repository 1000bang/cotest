package com.codingtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class dp_level2_01Test {

    private final dp_level2_01 solution = new dp_level2_01();

    @Test
    @DisplayName("예제 1: m=4, n=3, puddles=[[2,2]] → 4")
    void example1() {
        int[][] puddles = {{2, 2}};
        assertEquals(4, solution.solution(4, 3, puddles));
    }

    @Test
    @DisplayName("웅덩이 없음: m=3, n=3 → 6")
    void noPuddles() {
        int[][] puddles = {};
        assertEquals(6, solution.solution(3, 3, puddles));
    }

    @Test
    @DisplayName("한 줄 격자: m=5, n=1 → 1")
    void singleRow() {
        int[][] puddles = {};
        assertEquals(1, solution.solution(5, 1, puddles));
    }

    @Test
    @DisplayName("첫 행 웅덩이로 막힘: m=4, n=2, puddles=[[2,1]] → 0 경로 차단")
    void blockedFirstRow() {
        int[][] puddles = {{2, 1}};
        assertEquals(1, solution.solution(4, 2, puddles));
    }
}
