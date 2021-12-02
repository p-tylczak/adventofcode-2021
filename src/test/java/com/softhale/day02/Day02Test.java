package com.softhale.day02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    private final Day02 day02 = new Day02();

    @Test
    void part1_whenTestData_shouldReturn150() throws IOException {
        var result = day02.part1("src/test/resources/day02.txt");
        assertThat(result).isEqualTo(150);
    }

    @Test
    void part1_whenRealData_shouldReturn2039912() throws IOException {
        var result = day02.part1("src/main/resources/day02.txt");
        assertThat(result).isEqualTo(2039912);
    }

    @Test
    void part2_whenTestData_shouldReturn900() throws IOException {
        var result = day02.part2("src/test/resources/day02.txt");
        assertThat(result).isEqualTo(900);
    }

    @Test
    void part2_whenRealData_shouldReturn1942068080() throws IOException {
        var result = day02.part2("src/main/resources/day02.txt");
        assertThat(result).isEqualTo(1942068080);
    }

}
