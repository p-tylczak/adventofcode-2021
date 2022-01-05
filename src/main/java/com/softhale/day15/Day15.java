package com.softhale.day15;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
                line -> Arrays.stream(line.split("")).map(Integer::valueOf).toList());

        var graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        var cellByLocation = cells.stream()
                .peek(graph::addVertex)
                .collect(Collectors.toMap(this::toKey, Function.identity()));

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                var adjacent = findAdjacentCells(x, y, cellByLocation);
                var thisCell = cellByLocation.get(x + "_" + y);
                adjacent.forEach(a -> {
                    var edge = graph.addEdge(thisCell, a);
                    graph.setEdgeWeight(edge, a.content());
                });
            }
        }

        var alg = new DijkstraShortestPath<>(graph);
        var path = alg.getPath(
                cellByLocation.get("0_0"),
                cellByLocation.get((maxX - 1) + "_" + (maxY - 1)));

        return (long) path.getWeight();
    }

    private List<BoardUtils.Cell<Integer>> findAdjacentCells(int x, int y, Map<String, BoardUtils.Cell<Integer>> cells) {
        BiFunction<Integer, Integer, String> keyFn = (i1, i2) -> i1 + "_" + i2;

        var n = cells.get(keyFn.apply(x, y - 1));
        var s = cells.get(keyFn.apply(x, y + 1));
        var e = cells.get(keyFn.apply(x + 1, y));
        var w = cells.get(keyFn.apply(x - 1, y));

        return Stream.of(n, s, e, w)
                .filter(Objects::nonNull)
                .toList();
    }

    private String toKey(BoardUtils.Cell<Integer> cell) {
        return cell.location().x + "_" + cell.location().y;
    }

    public long part2(String filePath) {
        return 0L;
    }
}
