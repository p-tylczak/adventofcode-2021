package com.softhale.day10;

import com.softhale.utils.ParserUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {

    private final ParserUtils parserUtils = new ParserUtils();

    private static final Map<String, String> mapByClosingSymbol = Map.of(
            ")", "(",
            "]", "[",
            "}", "{",
            ">", "<");
    private static final Map<String, String> mapByOpeningSymbol = Map.of(
            "(", ")",
            "[", "]",
            "{", "}",
            "<", ">");
    private static final List<String> OPEN_SYMBOLS = List.of("(", "[", "{", "<");
    private static final Map<String, Long> SCORE_MAP = new HashMap<>();
    private static final Map<Character, Long> SCORE_MAP2 = new HashMap<>();

    static {
        SCORE_MAP.put(")", 3L);
        SCORE_MAP.put("]", 57L);
        SCORE_MAP.put("}", 1197L);
        SCORE_MAP.put(">", 25137L);

        SCORE_MAP2.put(')', 1L);
        SCORE_MAP2.put(']', 2L);
        SCORE_MAP2.put('}', 3L);
        SCORE_MAP2.put('>', 4L);
    }

    public long part1(String filePath) {
        var lines = parserUtils.readLines(filePath).stream()
                .map(line -> Stream.of(line.split("")).toList())
                .toList();

        var corrupted = new ArrayList<String>();

        for (var line : lines) {
            var result = traverse(line);
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

    private String traverse(List<String> characters) {
        var stack = new Stack<String>();

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
        var lines = parserUtils.readLines(filePath).stream()
                .map(line -> Stream.of(line.split("")).toList())
                .toList();

        var incomplete = new ArrayList<String>();

        for (var line : lines) {
            var result = traverse(line);
            if (result.isEmpty()) {
                incomplete.add(String.join("", line));
            }
        }

        var closing = new ArrayList<String>();

        for (var line : incomplete) {
            var stack = traverse2(Stream.of(line.split("")).toList());

            var c = stack.stream()
                    .map(mapByOpeningSymbol::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining());

            closing.add(new StringBuffer(c).reverse().toString());
        }

        var scores = new ArrayList<Long>();

        closing.forEach(line -> {
            var score = calculateScore(line);
            scores.add(score);
        });

        var sorted = scores.stream().sorted().toList();

        return sorted.get(scores.size() / 2);
    }

    private long calculateScore(String line) {
        var totalScore = 0L;

        for (var c : line.toCharArray()) {
            totalScore = totalScore * 5 + SCORE_MAP2.get(c);
        }

        return totalScore;
    }

    private Stack<String> traverse2(List<String> characters) {
        var stack = new Stack<String>();

        for (var character : characters) {
            if (OPEN_SYMBOLS.contains(character)) {
                stack.push(character);
            } else {
                stack.pop();
            }
        }

        return stack;
    }
}
