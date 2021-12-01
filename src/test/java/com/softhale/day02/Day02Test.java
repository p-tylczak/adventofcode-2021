package com.softhale.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;

class Day02Test {

    private final Day02 day02 = new Day02();

    @ParameterizedTest
    @MethodSource(value = "com.softhale.TestingSupport#dataPaths")
    void day2Part1(String fileName) throws IOException {
        day02.day2Part1(fileName + "day02.txt");
    }

    @ParameterizedTest
    @MethodSource(value = "com.softhale.TestingSupport#dataPaths")
    void day2Part2(String fileName) throws IOException {
        day02.day2Part1(fileName + "day02.txt");
    }

}
