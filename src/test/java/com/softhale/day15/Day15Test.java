package com.softhale.day15;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    private final Day15 day15 = new Day15();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day15.part1("src/test/resources/day15.txt", 10, 10);
        assertThat(result).isEqualTo(40L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day15.part1("src/main/resources/day15.txt", 100, 100);
        assertThat(result).isEqualTo(739L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day15.part2("src/test/resources/day15.txt", 10, 10);
        assertThat(result).isEqualTo(315L);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day15.part2("src/main/resources/day15.txt", 100, 100);
        assertThat(result).isEqualTo(3040L);
    }
}
