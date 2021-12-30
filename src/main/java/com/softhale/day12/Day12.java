package com.softhale.day12;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;
import com.softhale.utils.UndirectedGraph;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;

import java.util.Stack;

public class Day12 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public long part1(String filePath) {
        var edges = parserUtils.readLines(filePath).stream()
                .map(line -> {
                    var parts = line.split("-");
                    var from = parts[0];
                    var to = parts[1];

                    return new DefaultKeyValue<>(from, to);
                })
                .toList();

        var graph = new UndirectedGraph<String>();
        for (var edge : edges) {
            graph.addEdge(edge.getKey(), edge.getValue());
        }

        return new GraphPathCounterPart1<>(graph.getNodes())
                .countPaths("start", "end");
    }

    public long part2(String filePath) {
        var edges = parserUtils.readLines(filePath).stream()
                .map(line -> {
                    var parts = line.split("-");
                    var from = parts[0];
                    var to = parts[1];

                    return new DefaultKeyValue<>(from, to);
                })
                .toList();

        var graph = new UndirectedGraph<String>();
        for (var edge : edges) {
            graph.addEdge(edge.getKey(), edge.getValue());
        }

        return new GraphPathCounterPart2<>(graph.getNodes())
                .countPaths("start", "end");
    }
}
