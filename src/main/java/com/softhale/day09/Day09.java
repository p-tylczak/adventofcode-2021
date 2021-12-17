package com.softhale.day09;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public long part1(String filePath) {
        var cellsByLocation = boardUtils.toCells(
                        parserUtils.readLines(filePath),
                        l -> Stream.of(l.split("")).map(Long::valueOf).toList()).stream()
                .collect(Collectors.toMap(cell -> toKey(cell.location().x, cell.location().y), Function.identity()));

        return cellsByLocation.values().stream()
                .map(cell -> findLowestPoint(cell, cellsByLocation))
                .filter(Objects::nonNull)
                .map(BoardUtils.Cell::content)
                .map(value -> value + 1)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public long part2(String filePath) {
        var cellsByLocation = boardUtils.toCells(
                        parserUtils.readLines(filePath),
                        l -> Stream.of(l.split("")).map(Long::valueOf).toList()).stream()
                .collect(Collectors.toMap(cell -> toKey(cell.location().x, cell.location().y), Function.identity()));

        var lowestPoints = cellsByLocation.values().stream()
                .map(cell -> findLowestPoint(cell, cellsByLocation))
                .filter(Objects::nonNull)
                .toList();

        var basins = new ArrayList<List<BoardUtils.Cell<Long>>>();

        for (var cell : lowestPoints) {
            var basin = new ArrayList<BoardUtils.Cell<Long>>();
            findBasin(cell, basin, cellsByLocation);

            basins.add(basin);
        }

        var basinSizes = basins.stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .toList();

        return basinSizes.get(0)
                * basinSizes.get(1)
                * basinSizes.get(2).longValue();
    }

    private BoardUtils.Cell<Long> findLowestPoint(BoardUtils.Cell<Long> cell, Map<String, BoardUtils.Cell<Long>> cellsByLocation) {
        return findAdjacent(cell, cellsByLocation).allMatch(c -> c.content() > cell.content())
                ? cell
                : null;
    }

    private Stream<BoardUtils.Cell<Long>> findAdjacent(BoardUtils.Cell<Long> cell, Map<String, BoardUtils.Cell<Long>> cellsByLocation) {
        var nLoc = toKey(cell.location().x, cell.location().y - 1);
        var sLoc = toKey(cell.location().x, cell.location().y + 1);
        var eLoc = toKey(cell.location().x + 1, cell.location().y);
        var wLoc = toKey(cell.location().x - 1, cell.location().y);

        return Stream.of(nLoc, sLoc, eLoc, wLoc)
                .map(cellsByLocation::get)
                .filter(Objects::nonNull);
    }

    private String toKey(int x, int y) {
        return String.format("%s:%s", x, y);
    }

    private void findBasin(BoardUtils.Cell<Long> cell, ArrayList<BoardUtils.Cell<Long>> basin, Map<String, BoardUtils.Cell<Long>> cellsByLocation) {
        if (cell.content() == 9 || basin.contains(cell))
            return;

        basin.add(cell);
        findAdjacent(cell, cellsByLocation)
                .forEach(c -> findBasin(c, basin, cellsByLocation));

    }
}
