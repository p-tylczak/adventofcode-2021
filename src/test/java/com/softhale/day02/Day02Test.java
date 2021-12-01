package com.softhale.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

class Day02Test {

    private final Day02 day02 = new Day02();

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/day02.txt"})
    void part1(String fileName) throws IOException {
        day02.part1(fileName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/day02.txt", "src/main/resources/day02.txt"})
    void part2(String fileName) throws IOException {
        day02.part2(fileName);
    }

}
