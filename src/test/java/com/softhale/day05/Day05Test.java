package com.softhale.day05;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day05Test {

    private final Day05 day05 = new Day05();

    @Test
    void part1_whenTestData_shouldReturn5() {
        var result = day05.part1("src/test/resources/day05.txt");
        assertThat(result).isEqualTo(5L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day05.part1("src/main/resources/day05.txt");
        assertThat(result).isEqualTo(7438L);
    }

    @Test
    void part2_whenTestData_shouldReturn12() {
        var result = day05.part2("src/test/resources/day05.txt");
        assertThat(result).isEqualTo(12L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day05.part2("src/main/resources/day05.txt");
        assertThat(result).isEqualTo(21406L);
    }
}
