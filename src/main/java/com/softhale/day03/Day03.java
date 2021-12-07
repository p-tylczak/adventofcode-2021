package com.softhale.day03;

import com.softhale.utils.ParserUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day03 {

    private final ParserUtils parserUtils = new ParserUtils();

    public int part1(String filePath) {
        var lines = parserUtils.readLines(filePath);
        var content = new Character[lines.size()][];

        for (int y = 0; y < lines.size(); y++) {
            var characters = lines.get(y).toCharArray();
            content[y] = new Character[characters.length];

            for (int x = 0; x < characters.length; x++) {
                content[y][x] = characters[x];
            }
        }

        var transposed = new ArrayList<String>();
        var columnValues = new StringBuilder();

        for (int x = 0; x < content[0].length; x++) {
            for (Character[] characters : content) {
                columnValues.append(characters[x]);
            }

            transposed.add(columnValues.toString());
            columnValues = new StringBuilder();
        }

        var gammaRateStr = new StringBuilder();
        var epsilonRateStr = new StringBuilder();

        transposed.forEach(s -> {
            var countsOfZero = StringUtils.countMatches(s, '0');
            var countsOfOne = StringUtils.countMatches(s, '1');

            gammaRateStr.append(countsOfZero > countsOfOne ? "0" : "1");
            epsilonRateStr.append(countsOfZero < countsOfOne ? "0" : "1");
        });

        var gamma = Integer.parseInt(gammaRateStr.toString(), 2);
        var epsilon = Integer.parseInt(epsilonRateStr.toString(), 2);

        return gamma * epsilon;
    }

    public int part2(String filePath) {
        var lines = parserUtils.readLines(filePath);

        var cells = toCells(
                lines,
                s -> s.chars().mapToObj(c -> (char) c).toList());

        int oxygenGeneratorRating = calculateOxygenGeneratorRating(cells, lines, 0);
        int co2ScrubberRating = calculateCO2ScrubberRating(cells, lines, 0);

        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private int calculateOxygenGeneratorRating(List<Cell<Character>> cells, List<String> lines, int bitIndex) {
        if (lines.size() == 1) {
            return Integer.parseInt(lines.get(0), 2);
        }

        var transposed = extractColumnValues(cells, bitIndex);
        var frequency = countFrequency(transposed);
        var countOfOne = frequency.get('1');
        var countOfZero = frequency.get('0');
        var charToMatch = countOfOne >= countOfZero ? '1' : '0';

        var filteredLines = lines.stream()
                .filter(line -> line.charAt(bitIndex) == charToMatch)
                .toList();

        var filteredCells = toCells(
                filteredLines,
                s -> s.chars().mapToObj(c -> (char) c).toList());

        return calculateOxygenGeneratorRating(filteredCells, filteredLines, bitIndex + 1);
    }

    private int calculateCO2ScrubberRating(List<Cell<Character>> cells, List<String> lines, int bitIndex) {
        if (lines.size() == 1) {
            return Integer.parseInt(lines.get(0), 2);
        }

        var transposed = extractColumnValues(cells, bitIndex);
        var frequency = countFrequency(transposed);
        var countOfOne = frequency.get('1');
        var countOfZero = frequency.get('0');
        var charToMatch = countOfZero <= countOfOne ? '0' : '1';

        var filteredLines = lines.stream()
                .filter(line -> line.charAt(bitIndex) == charToMatch)
                .toList();

        var filteredCells = toCells(
                filteredLines,
                s -> s.chars().mapToObj(c -> (char) c).toList());

        return calculateCO2ScrubberRating(filteredCells, filteredLines, bitIndex + 1);
    }

    private Map<Character, Integer> countFrequency(String str) {
        var map = new HashMap<Character, Integer>();

        str.chars().forEach(c -> {
            var character = Character.valueOf((char) c);
            var counter = map.getOrDefault(character, 0);
            map.put((char) c, ++counter);
        });

        return map;
    }

    private String extractColumnValues(List<Cell<Character>> cells, int columnIndex) {
        var stringBuilder = new StringBuilder();

        cells.stream()
                .filter(c -> c.location().x == columnIndex)
                .forEach(c -> stringBuilder.append(c.content));

        return stringBuilder.toString();
    }

    private <T> List<Cell<T>> toCells(List<String> lines,
                                      Function<String, List<T>> lineToTypeConverterFn) {
        var cells = new ArrayList<Cell<T>>();

        for (int y = 0; y < lines.size(); y++) {
            var ts = lineToTypeConverterFn.apply(lines.get(y));

            for (int x = 0; x < ts.size(); x++) {
                var cell = new Cell<T>(new Point(x, y), ts.get(x));
                cells.add(cell);
            }
        }

        return cells;
    }


    public static record Cell<T>(Point location, T content) {
    }
}
