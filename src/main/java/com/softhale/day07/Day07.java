package com.softhale.day07;

import com.softhale.utils.ParserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day07 {

    private final ParserUtils parserUtils = new ParserUtils();

    public int part1(String filePath) {
        var numbers = Arrays.stream(parserUtils.readFileContent(filePath).trim().split(","))
                .map(Integer::valueOf)
                .toList();

        var average = numbers.stream().reduce(Integer::sum).map(sum -> sum / numbers.size()).orElse(0);

        var minFuel = new ArrayList<Integer>();
        minFuel.add(Integer.MAX_VALUE);

        IntStream.range(0, average)
                .forEach(i -> {
                    var sumOfDistances = numbers.stream()
                            .map(n -> Math.abs(i - n))
                            .reduce(Integer::sum)
                            .orElse(0);

                    if (sumOfDistances < minFuel.get(0)) {
                        minFuel.clear();
                        minFuel.add(0, sumOfDistances);
                        minFuel.add(1, i);
                    }
                });

        return minFuel.get(0);
    }

    public int part2(String filePath) {
        return 0;
    }
}
