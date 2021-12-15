package com.softhale.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtils {

    public String difference(String s1, String s2) {
        Function<String, List<String>> splitFn = s -> Arrays.stream(s.split("")).collect(Collectors.toList());

        return splitFn.apply(s1 + s2).stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining());
    }
}
