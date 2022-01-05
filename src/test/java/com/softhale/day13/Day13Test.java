package com.softhale.day13;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    private final Day13 day13 = new Day13();

    @Test
    void part1_whenTestData_shouldReturnCorrectValue() {
        var result = day13.part1("src/test/resources/day13.txt");
        assertThat(result).isEqualTo(17L);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day13.part1("src/main/resources/day13.txt");
        assertThat(result).isEqualTo(693L);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day13.part2("src/test/resources/day13.txt");
        assertThat(result).isEqualTo("UCLZRAZU");
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day13.part2("src/main/resources/day13.txt");
        assertThat(result).isEqualTo("UCLZRAZU");
    }
}
