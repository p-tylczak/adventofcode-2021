package com.softhale.day14;

import com.softhale.utils.ParserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 Have found this very useful: https://skarlso.github.io/2021/12/14/aoc-day14/
 */
public class Day14 {

    private final ParserUtils parserUtils = new ParserUtils();

    public long part1(String filePath) {
        return calculate(filePath, 10);
    }

    public long part2(String filePath) {
        return calculate(filePath, 40);
    }

    private long calculate(String filePath, int stepCount) {
        var chunks = parserUtils.getChunks(parserUtils.readLines(filePath));

        var polymerTemplate = new ArrayList<>(Stream.of(chunks.get(0).get(0).split("")).toList());

        var substitutionMap = chunks.get(1).stream()
                .collect(Collectors.toMap(s -> s.split(" -> ")[0], s -> s.split(" -> ")[1]));

        var pairsToTrack = new HashMap<String, AtomicLong>();

        for (int index = 0; index < polymerTemplate.size() - 1; index++) {
            var pair = polymerTemplate.get(index) + polymerTemplate.get(index + 1);

            pairsToTrack.compute(pair, (k, v) -> {
                var val = v == null ? new AtomicLong() : v;
                val.incrementAndGet();
                return val;
            });
        }

        for (int step = 1; step <= stepCount; step++) {
            var update = new HashMap<String, AtomicLong>();

            pairsToTrack.forEach(((k, v) -> {
                var keyOne = k.charAt(0) + substitutionMap.get(k);
                update.compute(keyOne, (key, val) -> add(val, v.get()));

                var keyTwo = substitutionMap.get(k) + k.charAt(1);
                update.compute(keyTwo, (key, val) -> add(val, v.get()));
            }));

            pairsToTrack = update;

            System.out.println("completed step = " + step);
        }

        var counts = new HashMap<Character, AtomicLong>();

        pairsToTrack.forEach((k, v) -> {
            counts.compute(k.charAt(0), (key, val) -> add(val, v.get()));
        });

        var lastCharInPolymer = (polymerTemplate.get(polymerTemplate.size() - 1).charAt(0));
        // since we're counting first character, we need to account for last
        // 1) 'B' in NNCB
        // 2) 'P' in PPFCHPFNCKOKOSBVCFPP
        counts.get(lastCharInPolymer).incrementAndGet();

        var min = counts.values().stream().map(AtomicLong::get).min(Long::compareTo).orElseThrow();
        var max = counts.values().stream().map(AtomicLong::get).max(Long::compareTo).orElseThrow();

        return max - min;
    }

    private AtomicLong add(AtomicLong number, long delta) {
        var counter = number == null
                ? new AtomicLong()
                : number;

        counter.getAndAdd(delta);

        return counter;
    }
}
