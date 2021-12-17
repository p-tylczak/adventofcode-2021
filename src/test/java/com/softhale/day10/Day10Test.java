package com.softhale.day10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    private final Day10 day10 = new Day10();

    @Test
    void part1_whenTestData_shouldReturn15() {
        var result = day10.part1("src/test/resources/day10.txt");
        assertThat(result).isEqualTo(26397L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day10.part1("src/main/resources/day10.txt");
        assertThat(result).isEqualTo(462693L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day10.part2("src/test/resources/day10.txt");
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day10.part2("src/main/resources/day10.txt");
        assertThat(result).isEqualTo(-1);
    }
}
