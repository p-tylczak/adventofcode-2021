package com.softhale.day12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test {

    private final Day12 day12 = new Day12();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day12.part1("src/test/resources/day12.txt");
        assertThat(result).isEqualTo(226L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day12.part1("src/main/resources/day12.txt");
        assertThat(result).isEqualTo(3230L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day12.part2("src/test/resources/day12.txt");
        assertThat(result).isEqualTo(3509L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day12.part2("src/main/resources/day12.txt");
        assertThat(result).isEqualTo(83475L);
    }
}
