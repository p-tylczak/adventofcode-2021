package com.softhale.day17;

import com.softhale.utils.ParserUtils;

import java.util.concurrent.atomic.AtomicLong;

public class Day17 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final AtomicLong versionSum = new AtomicLong();

    /* https://github.com/prendradjaja/advent-of-code-2021/blob/main/17--trick-shot/a.py */
    public int part1(int minY) {
        var velY = -minY - 1;

        var y = 0;
        while (velY > 0) {
            y += velY;
            velY -= 1;
        }

        return y;
    }

    public Long part2(String filePath) {
        return 0L;
    }
}
