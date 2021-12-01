package com.softhale.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParserUtilsTest {

    private ParserUtils parserUtils;

    @BeforeEach
    void createInstanceOfParserUtils() {
        this.parserUtils = new ParserUtils();
    }

    @Test
    void getChunks_whenFileContentContainsBlankLines_shouldReturnOnlyChunksWithData() {
        List<String> lines = Arrays.asList("abc\n", "efg\n", "\n", "hij\n", "\n");
        List<List<String>> result = parserUtils.getChunks(lines);

        List<List<String>> expected = Arrays.asList(Arrays.asList("abc", "efg"), Collections.singletonList("hij"));
        assertThat(result).hasSameElementsAs(expected);
    }

    @Test
    void getChunks_whenLastLineIsBlank_shouldReturnOnlyChunksWithData() {
        List<String> lines = Arrays.asList("abc\n", "efg\n", "\n", "hij\n", "\n");
        List<List<String>> result = parserUtils.getChunks(lines);

        List<List<String>> expected = Arrays.asList(Arrays.asList("abc", "efg"), Collections.singletonList("hij"));
        assertThat(result).hasSameElementsAs(expected);
    }

    @Test
    void getChunks_whenLastLineIsNotBlank_shouldReturnOnlyChunksWithData() {
        List<String> lines = Arrays.asList("abc\n", "efg\n", "\n", "hij\n", "klm\n");
        List<List<String>> result = parserUtils.getChunks(lines);

        List<List<String>> expected = Arrays.asList(Arrays.asList("abc", "efg"), Arrays.asList("hij", "klm"));
        assertThat(result).hasSameElementsAs(expected);
    }
}
