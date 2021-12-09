package com.softhale.day06;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day06Test {

    private final Day06 day06 = new Day06();

    @Test
    void part1_whenTestData_shouldReturn5934() {
        var result = day06.part1("src/test/resources/day06.txt", 80);
        assertThat(result).isEqualTo(5934L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day06.part1("src/main/resources/day06.txt", 80);
        assertThat(result).isEqualTo(389726L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day06.part1("src/test/resources/day06.txt", 256);
        assertThat(result).isEqualTo(26984457539L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day06.part1("src/main/resources/day06.txt", 256);
        assertThat(result).isEqualTo(1743335992042L);
    }
}
