package com.softhale.day04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day04Test {

    private final Day04 day04 = new Day04();

    @Test
    void part1_whenTestData_shouldReturn4512() {
        var result = day04.part1("src/test/resources/day04.txt");
        assertThat(result).isEqualTo(4512);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day04.part1("src/main/resources/day04.txt");
        assertThat(result).isEqualTo(31424);
    }

    @Test
    void part2_whenTestData_shouldReturn230() {
        var result = day04.part2("src/test/resources/day04.txt");
        assertThat(result).isEqualTo(230);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day04.part2("src/main/resources/day04.txt");
        assertThat(result).isEqualTo(2845944);
    }
}
