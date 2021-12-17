package com.softhale.day09;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    private final Day09 day09 = new Day09();

    @Test
    void part1_whenTestData_shouldReturn15() {
        var result = day09.part1("src/test/resources/day09.txt");
        assertThat(result).isEqualTo(15);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day09.part1("src/main/resources/day09.txt");
        assertThat(result).isEqualTo(548L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day09.part2("src/test/resources/day09.txt");
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day09.part2("src/main/resources/day09.txt");
        assertThat(result).isEqualTo(-1L);
    }
}
