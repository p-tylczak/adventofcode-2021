package com.softhale.day18;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    private final Day18 day18 = new Day18();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day18.part1("src/test/resources/day18.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day18.part1("src/test/resources/day18.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day18.part2("src/test/resources/day18.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day18.part2("src/main/resources/day18.txt");
        assertThat(result).isEqualTo(-1L);
    }
}
