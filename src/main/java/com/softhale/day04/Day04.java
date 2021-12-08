package com.softhale.day04;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.softhale.day04.Day04.BingoGameStatus.WON;
import static com.softhale.day04.Day04.BingoGameStatus.RUNNING;

public class Day04 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public int part1(String filePath) {
        var lines = parserUtils.readLines(filePath);
        var chunks = parserUtils.getChunks(lines);

        var bingoInput = Arrays.stream(chunks.get(0).get(0).split(","))
                .map(Integer::valueOf)
                .toList();
        var bingoBoards = chunks.stream().skip(1).toList();

        var winningBoard = playBingoGames(bingoInput, bingoBoards);

        var lastDrawnNumber = winningBoard.getLastDrawnNumber().orElse(0);
        var sumOfUnmarked = winningBoard.cells.stream()
                .filter(c -> !c.content().isMarked())
                .map(c -> c.content().getValue())
                .reduce(Integer::sum)
                .orElse(0);

        return lastDrawnNumber * sumOfUnmarked;
    }

    public int part2(String filePath) {
        return 0;
    }

    private BingoGame playBingoGames(List<Integer> bingoInput, List<List<String>> bingoBoards) {
        var bingoGames = bingoBoards.stream()
                .map(bingo -> boardUtils.toCells(bingo, this::lineToIntConvertFn))
                .map(BingoGame::new)
                .toList();

        for (var number : bingoInput) {
            for (BingoGame game : bingoGames) {
                game.play(number);

                if (game.getStatus() == WON) {
                    return game;
                }
            }
        }

        throw new IllegalStateException("Surely, some bingo boards must have won!");
    }

    private List<BingoCell> lineToIntConvertFn(String line) {
        return Arrays.stream(line.split(" "))
                .filter(StringUtils::isNotBlank)
                .map(Integer::valueOf)
                .map(BingoCell::new)
                .toList();
    }

    private static class BingoGame {
        private final List<BoardUtils.Cell<BingoCell>> cells;
        private BingoGameStatus status;
        private final List<Integer> drawnNumbers = new ArrayList<>();

        public BingoGame(List<BoardUtils.Cell<BingoCell>> cells) {
            this.cells = cells;
        }

        public void play(Integer number) {
            drawnNumbers.add(number);

            var cell = cells.stream()
                    .filter(c -> !c.content().isMarked())
                    .filter(c -> c.content().getValue() == number)
                    .findFirst();

            cell.ifPresent(c -> {
                c.content().setMarked(true);
                this.status = checkGameStatus(c);
            });
        }

        public BingoGameStatus getStatus() {
            return this.status;
        }

        public Optional<Integer> getLastDrawnNumber() {
            return drawnNumbers.size() == 0
                    ? Optional.empty()
                    : Optional.of(drawnNumbers.get(drawnNumbers.size() - 1));
        }

        private BingoGameStatus checkGameStatus(BoardUtils.Cell<BingoCell> cell) {
            var allMarkedVertically = cells.stream()
                    .filter(c -> c.location().x == cell.location().x)
                    .allMatch(c -> c.content().isMarked());

            var allMarkedHorizontally = cells.stream()
                    .filter(c -> c.location().y == cell.location().y)
                    .allMatch(c -> c.content().isMarked());

            return allMarkedHorizontally || allMarkedVertically ? WON : RUNNING;
        }
    }

    public static class BingoCell {
        private boolean marked;
        private final int value;

        public BingoCell(int value) {
            this.marked = false;
            this.value = value;
        }

        public boolean isMarked() {
            return marked;
        }

        public int getValue() {
            return value;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }
    }

    public enum BingoGameStatus {
        RUNNING,
        WON
    }
}
