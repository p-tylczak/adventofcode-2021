package com.softhale.utils;

import java.util.*;

public class UndirectedGraph<T> {

    private final Map<T, LinkedList<T>> nodes = new HashMap<>();

    public Map<T, LinkedList<T>> getNodes() {
        return nodes;
    }

    public void addEdge(T from, T to) {
        var fromEdges = nodes.getOrDefault(from, new LinkedList<>());
        var toEdges = nodes.getOrDefault(to, new LinkedList<>());

        if (!fromEdges.contains(to))
            fromEdges.add(to);

        if (!toEdges.contains(from))
            toEdges.add(from);

        nodes.put(from, fromEdges);
        nodes.put(to, toEdges);
    }
}
