package com.softhale.day08;

import com.softhale.utils.ParserUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day08 {

    private final ParserUtils parserUtils = new ParserUtils();

    public long part1(String filePath) {
        return parserUtils.readLines(filePath).stream()
                .flatMap(this::convertToSignals)
                .filter(Objects::nonNull)
                .count();
    }

    public long part2(String filePath) {
        return 0;
    }

    private Stream<Signal> convertToSignals(String line) {
        var outputValues = line.split(" \\| ")[1];
        var segments = Arrays.stream(outputValues.split(" "));

        return segments.map(this::toToSignal);
    }

    private Signal toToSignal(String signalStr) {
        final Function<Integer, Signal> createFn = digit -> new Signal(signalStr, digit);

        return switch (signalStr.length()) {
            case 2 -> createFn.apply(1);
            case 3 -> createFn.apply(7);
            case 4 -> createFn.apply(4);
            case 7 -> createFn.apply(8);
            default -> null;
        };
    }

    public static record Signal(String segments, int decodedDigit) {
    }
}
