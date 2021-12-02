package com.softhale.day03;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test {

    private final Day03 day03 = new Day03();

    @Test
    void part1_whenTestData_shouldReturn() throws IOException {
        var result = day03.part1("src/test/resources/day03.txt");
        assertThat(result).isEqualTo(0);
    }

    @Test
    void part1_whenRealData_shouldReturn() throws IOException {
        var result = day03.part1("src/main/resources/day03.txt");
        assertThat(result).isEqualTo(0);
    }

    @Test
    void part2_whenTestData_shouldReturn() throws IOException {
        var result = day03.part2("src/test/resources/day03.txt");
        assertThat(result).isEqualTo(0);
    }

    @Test
    void part2_whenRealData_shouldReturn() throws IOException {
        var result = day03.part2("src/main/resources/day03.txt");
        assertThat(result).isEqualTo(0);
    }
}
