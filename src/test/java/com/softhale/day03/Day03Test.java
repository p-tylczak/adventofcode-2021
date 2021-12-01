package com.softhale.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class Day03Test {

    private final Day03 day03 = new Day03();

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/day03.txt"})
    void part1(String fileName) throws IOException {
        day03.part1(fileName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/day03.txt", "src/main/resources/day03.txt"})
    void part2(String fileName) throws IOException {
        day03.part2(fileName);
    }
}
