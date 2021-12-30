package com.softhale.day12;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public record GraphPathCounterPart2<T>(Map<T, LinkedList<T>> nodes) {

    public int countPaths(T from, T to) {
        var pathCount = new AtomicInteger();

        for (T node : nodes.get(from)) {
            var visitedNodes = new Stack<T>();
            visitedNodes.push(from);

            countPaths(node, to, pathCount, visitedNodes);
        }

        return pathCount.get();
    }

    private void countPaths(T from, T to, AtomicInteger pathCount, Stack<T> visitedNodes) {
        visitedNodes.push(from);

        if (from.equals(to)) {
            pathCount.incrementAndGet();
        } else {
            for (T node : filteredNodes(from, visitedNodes)) {
                countPaths(node, to, pathCount, visitedNodes);
            }
        }

        visitedNodes.pop();
    }

    private List<T> filteredNodes(T from, Stack<T> visitedNodes) {
        var allowSmallCaveReenter = !hasVisitedAnySmallCaveTwiceAlready(visitedNodes);

        return nodes.get(from).stream()
                .filter(n -> !n.toString().equals("start"))
                .filter(n -> !isSmallCave(n) || (isSmallCave(n) && allowSmallCaveReenter) || (isSmallCave(n) && !visitedNodes.contains(n)))
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
