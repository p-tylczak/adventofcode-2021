package com.softhale.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {

    private final Day14 day14 = new Day14();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day14.part1("src/test/resources/day14.txt");
        assertThat(result).isEqualTo(1_588L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day14.part1("src/main/resources/day14.txt");
        assertThat(result).isEqualTo(2_027L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day14.part2("src/test/resources/day14.txt");
        assertThat(result).isEqualTo(2_188_189_693_529L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day14.part2("src/main/resources/day14.txt");
        assertThat(result).isEqualTo(2_265_039_461_737L);
    }
}
