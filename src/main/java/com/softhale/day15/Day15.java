package com.softhale.day15;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public long part1(String filePath, int maxX, int maxY) {
        var cells = boardUtils.toCells(
                parserUtils.readLines(filePath),
                line -> Arrays.stream(line.split(""))
                        .map(Integer::valueOf)
                        .toList());

        return findShortestPathWeight(maxX, maxY, cells);
    }

    public long part2(String filePath, int maxX, int maxY) {
        var cells = boardUtils.toCells(
                parserUtils.readLines(filePath),
                line -> Arrays.stream(line.split(""))
                        .map(Integer::valueOf)
                        .toList());

        var cellByLocation = cells.stream()
                .collect(Collectors.toMap(this::toKey, Function.identity()));

        var extendedCells = new ArrayList<>(cells);

        for (int index = 1; index < 15; index++) {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    var cell = cellByLocation.get(toKey(x, y));
                    var newContent = cell.content() + index;
                    if (newContent > 9) {
                        newContent = newContent - 9;
                    }

                    var newX = cell.location().x + (index * maxX);

                    var newCell = new BoardUtils.Cell<>(
                            new Point(newX, cell.location().y),
                            newContent);

                    extendedCells.add(newCell);
                }
            }
        }

        cellByLocation = extendedCells.stream()
                .collect(Collectors.toMap(this::toKey, Function.identity()));

        var newX = 0;
        var newY = 0;
        var newCells = new ArrayList<BoardUtils.Cell<Integer>>();

        for (int index = 0; index < 5; index++) {
            for (int y = 0; y < maxY; y++) {
                for (int x = index * maxX; x < maxX * 5 + (index * maxX); x++) {
                    // if (x % maxX == 0)
                    //    System.out.print(" ");

                    var cell = cellByLocation.get(toKey(x, y));
                    // System.out.print(cell.content());
                    newCells.add(new BoardUtils.Cell<>(
                            new Point(newX, newY),
                            cell.content()));

                    newX++;
                }

                newX = 0;
                newY++;
                System.out.println();
            }
        }

        return findShortestPathWeight(maxX * 5, maxY * 5, newCells);
    }

    private long findShortestPathWeight(int maxX, int maxY, List<BoardUtils.Cell<Integer>> cells) {
        var graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        var cellByLocation = cells.stream()
                .peek(graph::addVertex)
                .collect(Collectors.toMap(this::toKey, Function.identity()));

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                var adjacent = findAdjacentCells(x, y, cellByLocation);
                var thisCell = cellByLocation.get(toKey(x, y));
                adjacent.forEach(a -> {
                    var edge = graph.addEdge(thisCell, a);
                    graph.setEdgeWeight(edge, a.content());
                });
            }
        }

        var alg = new DijkstraShortestPath<>(graph);
        var path = alg.getPath(
                cellByLocation.get("0_0"),
                cellByLocation.get(toKey((maxX - 1), (maxY - 1))));

        return (long) path.getWeight();
    }

    private List<BoardUtils.Cell<Integer>> findAdjacentCells(int x, int y, Map<
            String, BoardUtils.Cell<Integer>> cells) {

        var n = cells.get(toKey(x, y - 1));
        var s = cells.get(toKey(x, y + 1));
        var e = cells.get(toKey(x + 1, y));
        var w = cells.get(toKey(x - 1, y));

        return Stream.of(n, s, e, w)
                .filter(Objects::nonNull)
                .toList();
    }

    private String toKey(BoardUtils.Cell<Integer> cell) {
        return toKey(cell.location().x, cell.location().y);
    }

    private String toKey(int x, int y) {
        return x + "_" + y;
    }
}
