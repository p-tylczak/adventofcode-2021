package com.softhale.day08;

import com.softhale.utils.ParserUtils;
import com.softhale.utils.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final StringUtils stringUtils = new StringUtils();

    public long part1(String filePath) {
        return parserUtils.readLines(filePath).stream()
                .flatMap(this::convertToSignals)
                .filter(s -> s.decodedDigit() != null)
                .count();
    }

    private Stream<Signal> convertToSignals(String line) {
        var outputValues = line.split(" \\| ")[1];
        var segments = Arrays.stream(outputValues.split(" "));

        return segments.map(this::toSignal);
    }

    // This helped with the code:
    // https://github.com/aormsby/advent-of-code-2021/blob/main/src/main/kotlin/d8_SevenSegmentSearch/SevenSegmentSearch.kt
    public long part2(String filePath) {
        return parserUtils.readLines(filePath).stream()
                .map(this::convertToSignals2)
                .map(Long::valueOf)
                .reduce(Long::sum)
                .orElseThrow();
    }

    private String convertToSignals2(String line) {
        var parts = line.split(" \\| ");
        var signalWires = Arrays.stream(parts[0].split(" "));
        var outputSegments = Arrays.stream(parts[1].split(" ")).map(this::toSignal);

        var signals = signalWires.map(this::toSignal).toList();

        var byLength = signals.stream()
                .collect(Collectors.groupingBy(s -> s.rawSegments().length()));

        var fiveSegments = new ArrayList<>(byLength.get(5));
        var sixSegments = new ArrayList<>(byLength.get(6));

        var one = byLength.get(2).stream().findFirst().orElseThrow();
        var four = byLength.get(4).stream().findFirst().orElseThrow();
        var seven = byLength.get(3).stream().findFirst().orElseThrow();
        var eight = byLength.get(7).stream().findFirst().orElseThrow();

        // 9 is the only 6-segment number to contain the segments of 7 and 4
        var nine = firstToSignal(9, sixSegments.stream()
                .filter(sixSegment -> thisContainsAllSegmentsOf(seven.rawSegments(), sixSegment.rawSegments()) && thisContainsAllSegmentsOf(four.rawSegments(), sixSegment.rawSegments())));

        sixSegments.removeIf(s -> s.rawSegments().equals(nine.rawSegments()));

        // 0 is the only remaining 6-segment number to contain the segments of 7
        var zero = firstToSignal(0, sixSegments.stream()
                .filter(sixSegment -> !sixSegment.rawSegments().equals(nine.rawSegments()))
                .filter(sixSegment -> thisContainsAllSegmentsOf(seven.rawSegments(), sixSegment.rawSegments())));

        sixSegments.removeIf(s -> s.rawSegments().equals(zero.rawSegments()));


        // 6 is the only 6-segment number remaining
        var six = firstToSignal(6, sixSegments.stream());

        // 3 is the only 5-segment number to contain the segments of 7
        var three = firstToSignal(3, fiveSegments.stream()
                .filter(fiveSegment -> thisContainsAllSegmentsOf(seven.rawSegments(), fiveSegment.rawSegments())));

        fiveSegments.removeIf(s -> s.rawSegments().equals(three.rawSegments()));


        // To obtain five, I got the diffs of 3, 0, and 6
        // The remaining segments are inside 5's segments (but not 2's)
        var five = firstToSignal(5, fiveSegments.stream()
                .filter(fiveSegment -> {
                    var diff = stringUtils.difference(stringUtils.difference(three.rawSegments(), zero.rawSegments()), six.rawSegments());
                    return thisContainsAllSegmentsOf(diff, fiveSegment.rawSegments());
                }));

        fiveSegments.removeIf(s -> s.rawSegments().equals(five.rawSegments()));


        // 2 is the only 5-segment number remaining
        var two = firstToSignal(2, fiveSegments.stream());

        var signalMap = new HashMap<String, Signal>();
        Consumer<Signal> putFn = s -> signalMap.put(Arrays.stream(s.rawSegments().split("")).sorted().collect(Collectors.joining()), s);
        putFn.accept(zero);
        putFn.accept(one);
        putFn.accept(two);
        putFn.accept(three);
        putFn.accept(four);
        putFn.accept(five);
        putFn.accept(six);
        putFn.accept(seven);
        putFn.accept(eight);
        putFn.accept(nine);

        return outputSegments
                .map(os -> signalMap.values().stream()
                        .filter(s -> s.rawSegments().equals(os.rawSegments()))
                        .findFirst()
                        .orElseThrow())
                .map(Signal::decodedDigit)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private Signal firstToSignal(int digit, Stream<Signal> signalStream) {
        return signalStream.findFirst()
                .map(s -> new Signal(s.rawSegments(), digit))
                .orElseThrow();
    }

    private boolean thisContainsAllSegmentsOf(String of, String thisSignal) {
        Function<String, List<String>> splitFn = s -> Arrays.stream(s.split("")).toList();

        return splitFn.apply(thisSignal)
                .containsAll(splitFn.apply(of));
    }

    private Signal toSignal(String signalStr) {
        Function<String, String> sortFn = s -> Arrays.stream(s.split("")).sorted().collect(Collectors.joining());
        final Function<Integer, Signal> createFn = digit -> new Signal(sortFn.apply(signalStr), digit);

        return switch (signalStr.length()) {
            case 2 -> createFn.apply(1);
            case 3 -> createFn.apply(7);
            case 4 -> createFn.apply(4);
            case 7 -> createFn.apply(8);
            default -> createFn.apply(null);
        };
    }

    public static record Signal(String rawSegments, Integer decodedDigit) {
    }
}
