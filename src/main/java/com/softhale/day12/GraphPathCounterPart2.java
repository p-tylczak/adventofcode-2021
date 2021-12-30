package com.softhale.day12;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GraphPathCounterPart2<T> {

    private final Map<T, LinkedList<T>> nodes;

    public GraphPathCounterPart2(Map<T, LinkedList<T>> nodes) {
        this.nodes = nodes;
    }

    public int countPaths(T from, T to) {
        var pathCount = new AtomicInteger();

        var visitCountMap = new HashMap<T, AtomicInteger>();
        nodes.keySet().forEach(node -> visitCountMap.put(node, new AtomicInteger()));

        for (T node : nodes.get(from)) {
            var visitedNodes = new Stack<T>();
            visitedNodes.push(from);

            countPaths(node, to, pathCount, visitedNodes, visitCountMap, true);
        }

        return pathCount.get();
    }

    private void countPaths(T from, T to, AtomicInteger pathCount, Stack<T> visitedNodes, Map<T, AtomicInteger> visitCountMap, boolean allowSmallCave) {
        visitedNodes.push(from);
        if (!from.toString().equals("end"))
            visitCountMap.get(from).incrementAndGet();

        if (from.equals(to)) {
            pathCount.incrementAndGet();
            // System.out.println("visitedNodes = " + visitedNodes);
        } else {
            for (T node : filteredNodes(from)) {
                var allowSmallCaveReenter = !hasVisitedAnySmallCaveTwiceAlready(visitedNodes);
                if (!isSmallCave(node) || (isSmallCave(node) && allowSmallCaveReenter) || (isSmallCave(node) && !visitedNodes.contains(node)))
                    countPaths(node, to, pathCount, visitedNodes, visitCountMap, allowSmallCaveReenter);
            }
        }

        visitedNodes.pop();
    }

    private List<T> filteredNodes(T from) {
        return nodes.get(from).stream()
                .filter(n -> !n.toString().equals("start"))
                .toList();
    }

    private boolean hasVisitedAnySmallCaveTwiceAlready(Stack<T> visitedNodes) {
        return visitedNodes.stream()
                .filter(this::isSmallCave)
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .anyMatch(e -> e.getValue() > 1);
    }

    private boolean isSmallCave(T node) {
        return Character.isLowerCase(node.toString().charAt(0));
    }
}
