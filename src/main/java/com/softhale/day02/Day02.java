package com.softhale.day02;

import com.softhale.utils.ParserUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Day02 {

    private final ParserUtils parserUtils = new ParserUtils();

    public int part1(String filePath) {
        var horizontalPosition = new AtomicInteger();
        var depth = new AtomicInteger();

        parserUtils.parse(filePath, this::toCommand)
                .forEach(cmd -> {
                    switch (cmd.cmd()) {
                        case forward -> horizontalPosition.getAndAdd(cmd.value());
                        case down -> depth.getAndAdd(cmd.value());
                        case up -> depth.getAndAdd(-cmd.value());
                    }
                });

        return horizontalPosition.get() * depth.get();
    }

    public int part2(String filePath) {
        var horizontalPosition = new AtomicInteger();
        var depth = new AtomicInteger();
        var aim = new AtomicInteger();

        parserUtils.parse(filePath, this::toCommand)
                .forEach(cmd -> {
                    switch (cmd.cmd()) {
                        case forward -> {
                            horizontalPosition.getAndAdd(cmd.value());
                            depth.getAndAdd(aim.get() * cmd.value());
                        }
                        case down -> aim.getAndAdd(cmd.value());
                        case up -> aim.getAndAdd(-cmd.value());
                    }
                });

        return horizontalPosition.get() * depth.get();
    }

    private Command toCommand(String s) {
        var parts = s.split(" ");
        return new Command(Cmd.valueOf(parts[0]), Integer.parseInt(parts[1]));
    }
}
