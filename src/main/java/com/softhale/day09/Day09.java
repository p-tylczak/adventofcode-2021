package com.softhale.day09;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;
import com.softhale.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final StringUtils stringUtils = new StringUtils();
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
                .map(this::calculateRiskLevel)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private Long calculateRiskLevel(Long value) {
        return value + 1;
    }

    private BoardUtils.Cell<Long> findLowestPoint(BoardUtils.Cell<Long> cell, Map<String, BoardUtils.Cell<Long>> cellsByLocation) {
        var nLoc = toKey(cell.location().x, cell.location().y - 1);
        var sLoc = toKey(cell.location().x, cell.location().y + 1);
        var eLoc = toKey(cell.location().x + 1, cell.location().y);
        var wLoc = toKey(cell.location().x - 1, cell.location().y);

        return Stream.of(nLoc, sLoc, eLoc, wLoc)
                .map(cellsByLocation::get)
                .filter(Objects::nonNull)
                .allMatch(c -> c.content() > cell.content())
                ? cell
                : null;
    }

    private String toKey(int x, int y) {
        return String.format("%s:%s", x, y);
    }

    public long part2(String filePath) {
        return 0;
    }
}
