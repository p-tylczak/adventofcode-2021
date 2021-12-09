package com.softhale.day06;

import com.softhale.utils.ParserUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Day06 {

    private final ParserUtils parserUtils = new ParserUtils();

    public long part1(String filePath, int numberOfDays) {
        var internalTimers = Arrays.stream(parserUtils.readFileContent(filePath).trim().split(","))
                .map(Integer::valueOf)
                .toList();

        var map = new HashMap<Integer, AtomicLong>();

        IntStream.range(0, 9).forEach(i -> map.put(i, new AtomicLong()));

        for (var internalTimer : internalTimers) {
            var counter = map.get(internalTimer);
            counter.incrementAndGet();
        }

        for (int count = 1; count <= numberOfDays; count++) {
            var countOfNewborns = map.get(0).get();

            map.put(0, map.get(1));
            map.put(1, map.get(2));
            map.put(2, map.get(3));
            map.put(3, map.get(4));
            map.put(4, map.get(5));
            map.put(5, map.get(6));
            map.put(6, map.get(7));
            map.put(7, map.get(8));
            map.put(8, new AtomicLong());

            if (countOfNewborns > 0) {
                map.put(6, new AtomicLong(map.get(6).get() + countOfNewborns));
                map.put(8, new AtomicLong(countOfNewborns));
            }
        }

        return map.values().stream()
                .map(AtomicLong::get)
                .reduce(Long::sum)
                .orElse(0L);
    }
}
