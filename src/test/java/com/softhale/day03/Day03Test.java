package com.softhale.day03;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test {

    private final Day03 day03 = new Day03();

    @Test
    void part1_whenTestData_shouldReturn198() {
        var result = day03.part1("src/test/resources/day03.txt");
        assertThat(result).isEqualTo(198);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day03.part1("src/main/resources/day03.txt");
        assertThat(result).isEqualTo(2648450);
    }

    @Test
    void part2_whenTestData_shouldReturn230() {
        var result = day03.part2("src/test/resources/day03.txt");
        assertThat(result).isEqualTo(230);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day03.part2("src/main/resources/day03.txt");
        assertThat(result).isEqualTo(2845944);
    }
}
