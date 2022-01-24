package com.softhale.day16;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    private final Day16 day16 = new Day16();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day16.part1("src/test/resources/day16.txt");
        assertThat(result).isEqualTo(31L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day16.part1("src/main/resources/day16.txt");
        assertThat(result).isEqualTo(965L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day16.part2("src/test/resources/day16.txt");
        assertThat(result).isEqualTo(54L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day16.part2("src/main/resources/day16.txt");
        assertThat(result).isEqualTo(116672213160L);
    }
}
