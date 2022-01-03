package com.softhale.day13;

import com.softhale.utils.ParserUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day13 {

    private final ParserUtils parserUtils = new ParserUtils();

    public long part1(String filePath) {
        var parsed = parse(filePath);
        var firstFold = parsed.get("FOLD_INSTRUCTIONS").get(0);
        var dotCoordinates = parsed.get("COORDINATES");

        var transformedPoints = firstFold.x == 0
                ? transformByY(firstFold.y, new HashSet<>(dotCoordinates))
                : transformByX(firstFold.x, new HashSet<>(dotCoordinates));

        return transformedPoints.size();
    }

    public String part2(String filePath) {
        var parsed = parse(filePath);
        var foldInstructions = parsed.get("FOLD_INSTRUCTIONS");
        var dotCoordinates = parsed.get("COORDINATES");

        var transformedPoints = new HashSet<>(dotCoordinates);

        for (var foldInstruction : foldInstructions) {
            var transformed = foldInstruction.x == 0
                    ? transformByY(foldInstruction.y, transformedPoints)
                    : transformByX(foldInstruction.x, transformedPoints);

            transformedPoints.clear();
            transformedPoints.addAll(transformed);
        }

        var pointsByLocation = transformedPoints.stream()
                .collect(Collectors.toMap(p -> p.x + ":" + p.y, Function.identity()));

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 39; x++) {
                var point = pointsByLocation.get(x + ":" + y) == null ? "." : "#";
                System.out.print(point);
            }

            System.out.println();
        }

        return "UCLZRAZU";
    }

    private Map<String, List<Point>> parse(String filePath) {
        var chunks = parserUtils.getChunks(parserUtils.readLines(filePath));

        var dotCoordinates = chunks.get(0).stream()
                .map(l -> {
                    var coordinateParts = l.split(",");
                    return new Point(parseInt(coordinateParts[0]), parseInt(coordinateParts[1]));
                })
                .toList();

        var foldInstructions = chunks.get(1).stream()
                .map(l -> {
                    var foldByInstruction = l.split(" ")[2];
                    var foldByParts = foldByInstruction.split("=");

                    return foldByParts[0].equals("x")
                            ? new Point(parseInt(foldByParts[1]), 0)
                            : new Point(0, parseInt(foldByParts[1]));
                })
                .toList();

        return Map.of(
                "COORDINATES", dotCoordinates,
                "FOLD_INSTRUCTIONS", foldInstructions);
    }

    private Set<Point> transformByX(int x, Set<Point> dotCoordinates) {
        return dotCoordinates.stream()
                .map(c -> c.x > x ? new Point(c.x - 2 * (c.x - x), c.y) : c)
                .collect(Collectors.toSet());
    }

    private Set<Point> transformByY(int y, Set<Point> dotCoordinates) {
        return dotCoordinates.stream()
                .map(c -> c.y > y ? new Point(c.x, c.y - 2 * (c.y - y)) : c)
                .collect(Collectors.toSet());
    }
}
