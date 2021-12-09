package com.softhale.day05;

import com.softhale.utils.ParserUtils;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.*;

public class Day05 {

    private final ParserUtils parserUtils = new ParserUtils();

    public long part1(String filePath) {
        final List<Point> allPoints = getPoints(filePath, l -> l.from.x == l.to.x || l.from.y == l.to.y);
        return calculateSumOfIntersectionCounts(allPoints);
    }

    public long part2(String filePath) {
        final List<Point> allPoints = getPoints(filePath, l -> true);
        return calculateSumOfIntersectionCounts(allPoints);
    }

    private long calculateSumOfIntersectionCounts(List<Point> allPoints) {
        var pointMap = new HashMap<Point, AtomicInteger>();

        for (Point point : allPoints) {
            var counter = pointMap.getOrDefault(point, new AtomicInteger());
            counter.incrementAndGet();
            pointMap.put(point, counter);
        }

        var occurrenceByValue = pointMap.values().stream()
                .map(AtomicInteger::get)
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        occurrenceByValue.remove(1);

        return occurrenceByValue.values().stream()
                .reduce(Long::sum)
                .orElse(0L);
    }

    private List<Point> getPoints(String filePath, Predicate<Line> filter) {
        final Function<String, Point> toPointFn = s -> new Point(
                parseInt(s.split(",")[0]),
                parseInt(s.split(",")[1]));

        return parserUtils.readLines(filePath).stream()
                .map(line -> {
                    var parts = line.split(" -> ");
                    var p1 = parts[0];
                    var p2 = parts[1];

                    return new Line(toPointFn.apply(p1), toPointFn.apply(p2));
                })
                .filter(filter)
                .map(this::calculatePoints)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Point> calculatePoints(Line line) {
        var slopeNominator = line.to.y - line.from.y;
        var slopeDenominator = line.to.x - line.from.x;

        var fromX = min(line.from.x, line.to.x);
        var toX = max(line.from.x, line.to.x);

        if (slopeNominator == 0) { // horizontal
            return IntStream.range(fromX, toX + 1)
                    .mapToObj(i -> new Point(i, line.from.y))
                    .toList();
        }

        if (slopeDenominator == 0) { // vertical
            var fromY = min(line.from.y, line.to.y);
            var toY = max(line.from.y, line.to.y);
            return IntStream.range(fromY, toY + 1)
                    .mapToObj(i -> new Point(line.from.x, i))
                    .toList();
        }

        // m = (y2 - y1) / (x2 - x1)
        // y = mx + b
        // b = y - mx
        var slope = slopeNominator / slopeDenominator;
        var b = line.from.y - slope * line.from.x;
        final UnaryOperator<Integer> yFn = x -> slope * x + b;

        return IntStream.range(fromX, toX + 1)
                .mapToObj(i -> new Point(i, yFn.apply(i)))
                .toList();
    }

    private static record Line(Point from, Point to) {
    }
}
