package com.softhale.day08;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    private final Day08 day08 = new Day08();

    @Test
    void part1_whenTestData_shouldReturn37() {
        var result = day08.part1("src/test/resources/day08.txt");
        assertThat(result).isEqualTo(26);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day08.part1("src/main/resources/day08.txt");
        assertThat(result).isEqualTo(247L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day08.part2("src/test/resources/day08.txt");
        assertThat(result).isEqualTo(168);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day08.part2("src/main/resources/day08.txt");
        assertThat(result).isEqualTo(98231647);
    }
}
