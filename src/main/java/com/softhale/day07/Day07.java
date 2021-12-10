package com.softhale.day07;

import com.softhale.utils.ParserUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class Day07 {

    private static final String FUEL_CONSUMPTION = "FUEL_CONSUMPTION";
    private static final String HORIZONTAL_POSITION = "HORIZONTAL_POSITION";

    private final ParserUtils parserUtils = new ParserUtils();

    public int part1(String filePath) {
        return calculateFuelConsumption(filePath, UnaryOperator.identity());
    }

    public int part2(String filePath) {
        return calculateFuelConsumption(filePath, this::fuelConsumptionStrategy);
    }

    private Integer calculateFuelConsumption(String filePath, UnaryOperator<Integer> fuelConsumptionStrategy) {
        var numbers = Arrays.stream(parserUtils.readFileContent(filePath).trim().split(","))
                .map(Integer::valueOf)
                .toList();

        // this is just to limit number of possible horizontal positions
        int average = numbers.stream().reduce(Integer::sum).map(sum -> sum / numbers.size()).orElse(0);

        var result = new HashMap<String, Integer>();
        result.put(FUEL_CONSUMPTION, Integer.MAX_VALUE);

        IntStream.range(0, average + average / 2)
                .forEach(potentialHorizontalPosition -> {
                    var sumOfDistances = numbers.stream()
                            .map(n -> Math.abs(potentialHorizontalPosition - n))
                            .map(fuelConsumptionStrategy)
                            .reduce(Integer::sum)
                            .orElse(0);

                    if (sumOfDistances < result.get(FUEL_CONSUMPTION)) {
                        result.clear();
                        result.put(FUEL_CONSUMPTION, sumOfDistances);
                        result.put(HORIZONTAL_POSITION, potentialHorizontalPosition);
                    }
                });

        return result.get(FUEL_CONSUMPTION);
    }

    private int fuelConsumptionStrategy(int distance) {
        int result = 0;

        for (int d = 1; d <= distance; d++) {
            result += d;
        }

        return result;
    }
}
