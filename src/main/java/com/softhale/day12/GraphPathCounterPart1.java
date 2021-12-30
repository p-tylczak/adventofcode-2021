package com.softhale.day12;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphPathCounterPart1<T> {

    private final Map<T, LinkedList<T>> nodes;

    public GraphPathCounterPart1(Map<T, LinkedList<T>> nodes) {
        this.nodes = nodes;
    }

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
            for (T node : nodes.get(from).stream().filter(n -> !(isSmallCave(n) && visitedNodes.contains(n))).sorted().toList()) {
                countPaths(node, to, pathCount, visitedNodes);
            }
        }

        visitedNodes.pop();
    }

    private boolean isSmallCave(T node) {
        return Character.isLowerCase(node.toString().charAt(0));
    }
}
