package com.softhale.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    private final StringUtils stringUtils = new StringUtils();

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void difference_shouldCorrectlyFindStringDifference(String input, String result) {
        var parts = input.split(":");
        var s1 = parts[0];
        var s2 = parts[1];

        var diff = stringUtils.difference(s1, s2);
        assertThat(diff).isEqualTo(result);
    }

    static Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of("abc:abc", ""),
                Arguments.of("abc:acd", "bd"),
                Arguments.of("abc:def", "abcdef")
        );
    }

}
