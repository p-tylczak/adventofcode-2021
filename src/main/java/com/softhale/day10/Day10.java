package com.softhale.day10;

import com.softhale.utils.ParserUtils;

import java.util.*;
import java.util.stream.Stream;

public class Day10 {

    private final ParserUtils parserUtils = new ParserUtils();

    private static final Map<String, String> mapByClosingSymbol = Map.of(
            ")", "(",
            "]", "[",
            "}", "{",
            ">", "<");
    private static final List<String> OPEN_SYMBOLS = List.of("(", "[", "{", "<");
    private static final Map<String, Long> SCORE_MAP = new HashMap<>();

    static {
        SCORE_MAP.put(")", 3L);
        SCORE_MAP.put("]", 57L);
        SCORE_MAP.put("}", 1197L);
        SCORE_MAP.put(">", 25137L);
    }

    public long part1(String filePath) {
        var lines = parserUtils.readLines(filePath).stream()
                .map(line -> Stream.of(line.split("")).toList())
                .toList();

        var stack = new Stack<String>();
        var corrupted = new ArrayList<String>();

        for (var line : lines) {
            var result = traverse(line, stack);
            if (!result.isEmpty()) {
                corrupted.add(result);
            }
        }

        return corrupted.stream()
                .map(SCORE_MAP::get)
                .filter(Objects::nonNull)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private String traverse(List<String> characters, Stack<String> stack) {
        for (var character : characters) {
            if (OPEN_SYMBOLS.contains(character)) {
                stack.push(character);
            } else {
                var popped = stack.pop();
                var openingSymbol = mapByClosingSymbol.getOrDefault(character, "");

                if (!openingSymbol.equals(popped)) {
                    return character;
                }
            }
        }

        return "";
    }

    public long part2(String filePath) {
        return 0;
    }
}
