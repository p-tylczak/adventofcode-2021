package com.softhale.day11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    private final Day11 day11 = new Day11();

    @Test
    void part1_whenTestData_shouldReturn1656() {
        var result = day11.part1("src/test/resources/day11.txt");
        assertThat(result).isEqualTo(1656L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day11.part1("src/main/resources/day11.txt");
        assertThat(result).isEqualTo(1773L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day11.part2("src/test/resources/day11.txt");
        assertThat(result).isEqualTo(195);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day11.part2("src/main/resources/day11.txt");
        assertThat(result).isEqualTo(494);
    }
}
