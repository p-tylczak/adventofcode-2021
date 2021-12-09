package com.softhale.day07;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day07Test {

    private final Day07 day07 = new Day07();

    @Test
    void part1_whenTestData_shouldReturn37() {
        var result = day07.part1("src/test/resources/day07.txt");
        assertThat(result).isEqualTo(37);
    }

    @Test
    void part1_whenRealData_shouldReturnCorrectValue() {
        var result = day07.part1("src/main/resources/day07.txt");
        assertThat(result).isEqualTo(348996);
    }

    @Test
    void part2_whenTestData_shouldReturnCorrectValue() {
        var result = day07.part2("src/test/resources/day07.txt");
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void part2_whenRealData_shouldReturnCorrectValue() {
        var result = day07.part2("src/main/resources/day07.txt");
        assertThat(result).isEqualTo(-1);
    }
}
