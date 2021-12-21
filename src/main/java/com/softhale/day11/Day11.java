package com.softhale.day11;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public long part1(String filePath) {
        Map<String, BoardUtils.Cell<AtomicInteger>> octopuses = getOctopusByLocation(filePath);

        var flashCounter = new AtomicInteger();
        var flashedOctopuses = new ArrayList<BoardUtils.Cell<AtomicInteger>>();

        for (int step = 1; step <= 100; step++) {
            checkForFlashes(octopuses.values().stream().toList(), octopuses, flashedOctopuses);
            flashCounter.addAndGet(flashedOctopuses.size());

            print(octopuses, step + 1);

            flashedOctopuses.clear();
        }

        return flashCounter.get();
    }

    public long part2(String filePath) {
        Map<String, BoardUtils.Cell<AtomicInteger>> octopuses = getOctopusByLocation(filePath);
        var flashedOctopuses = new ArrayList<BoardUtils.Cell<AtomicInteger>>();

        for (int step = 1; step <= 1_000; step++) {
            checkForFlashes(octopuses.values().stream().toList(), octopuses, flashedOctopuses);

            if (flashedOctopuses.size() == 100)
                return step;

            flashedOctopuses.clear();
        }

        throw new RuntimeException();
    }

    private Map<String, BoardUtils.Cell<AtomicInteger>> getOctopusByLocation(String filePath) {
        return boardUtils.toCells(
                        parserUtils.readLines(filePath),
                        l -> Stream.of(l.split(""))
                                .map(Integer::valueOf)
                                .map(AtomicInteger::new)
                                .toList()).stream()
                .collect(Collectors.toMap(cell -> toKey(cell.location().x, cell.location().y), Function.identity()));
    }

    private void checkForFlashes(List<BoardUtils.Cell<AtomicInteger>> octopuses,
                                 Map<String, BoardUtils.Cell<AtomicInteger>> octopusByLocation,
                                 ArrayList<BoardUtils.Cell<AtomicInteger>> flashedOctopuses) {
        octopuses.forEach(o -> o.content().incrementAndGet());

        octopuses.forEach(o -> {
            if (o.content().get() > 9) {
                flashedOctopuses.add(o);

                var adjacent = new ArrayList<>(findAdjacent(o, octopusByLocation).toList());
                adjacent.removeAll(flashedOctopuses);
                checkForFlashes(adjacent, octopusByLocation, flashedOctopuses);

                o.content().set(0);
            }
        });
    }

    private void print(Map<String, BoardUtils.Cell<AtomicInteger>> values, int step) {
        System.out.println("After step " + step);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                var key = toKey(j, i);
                System.out.print(values.get(key).content());
            }

            System.out.println();
        }

        System.out.println();
    }

    private Stream<BoardUtils.Cell<AtomicInteger>> findAdjacent(BoardUtils.Cell<AtomicInteger> cell, Map<String, BoardUtils.Cell<AtomicInteger>> cellsByLocation) {
        var n = toKey(cell.location().x, cell.location().y - 1);
        var ne = toKey(cell.location().x + 1, cell.location().y - 1);
        var e = toKey(cell.location().x + 1, cell.location().y);
        var se = toKey(cell.location().x + 1, cell.location().y + 1);
        var s = toKey(cell.location().x, cell.location().y + 1);
        var sw = toKey(cell.location().x - 1, cell.location().y + 1);
        var w = toKey(cell.location().x - 1, cell.location().y);
        var nw = toKey(cell.location().x - 1, cell.location().y - 1);

        return Stream.of(n, ne, e, se, s, sw, w, nw)
                .map(cellsByLocation::get)
                .filter(Objects::nonNull);
    }

    private String toKey(int x, int y) {
        return String.format("%s:%s", x, y);
    }
}
