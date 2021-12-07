package com.softhale.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BoardUtils {

    public <T> List<Cell<T>> toCells(List<String> lines,
                                      Function<String, List<T>> lineToTypeConverterFn) {
        var cells = new ArrayList<Cell<T>>();

        for (int y = 0; y < lines.size(); y++) {
            var ts = lineToTypeConverterFn.apply(lines.get(y));

            for (int x = 0; x < ts.size(); x++) {
                var cell = new Cell<T>(new Point(x, y), ts.get(x));
                cells.add(cell);
            }
        }

        return cells;
    }

    public static record Cell<T>(Point location, T content) {
    }
}
