package com.softhale.day17;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    private final Day17 day17 = new Day17();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day17.part1("src/test/resources/day17.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day17.part1("src/main/resources/day17.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day17.part2("src/test/resources/day17.txt");
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day17.part2("src/main/resources/day17.txt");
        assertThat(result).isEqualTo(-1L);
    }
}
