package com.softhale.day03;

import com.softhale.utils.ParserUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Day03 {

    private final ParserUtils parserUtils = new ParserUtils();

    public int part1(String filePath) throws IOException {
        var lines = parserUtils.readLines(filePath);
        var content = new Character[lines.size()][];

        for (int y = 0; y < lines.size(); y++) {
            var characters = lines.get(y).toCharArray();
            content[y] = new Character[characters.length];

            for (int x = 0; x < characters.length; x++) {
                content[y][x] = characters[x];
            }
        }

        var transposed = new ArrayList<String>();
        var columnValues = new StringBuilder();

        for (int x = 0; x < content[0].length; x++) {
            for (Character[] characters : content) {
                columnValues.append(characters[x]);
            }

            transposed.add(columnValues.toString());
            columnValues = new StringBuilder();
        }

        var gammaRateStr = new StringBuilder();
        var epsilonRateStr = new StringBuilder();

        transposed.forEach(s -> {
            var countsOfZero = StringUtils.countMatches(s, '0');
            var countsOfOne = StringUtils.countMatches(s, '1');

            gammaRateStr.append(countsOfZero > countsOfOne ? "0" : "1");
            epsilonRateStr.append(countsOfZero < countsOfOne ? "0" : "1");
        });

        var gamma = Integer.parseInt(gammaRateStr.toString(), 2);
        var epsilon = Integer.parseInt(epsilonRateStr.toString(), 2);

        return gamma * epsilon;
    }

    public int part2(String filePath) throws IOException {
        return 0;
    }
}
