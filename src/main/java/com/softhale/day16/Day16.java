package com.softhale.day16;

import com.softhale.utils.BoardUtils;
import com.softhale.utils.ParserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final BoardUtils boardUtils = new BoardUtils();

    public long part1(String filePath) {
        var binaryString = Stream.of(parserUtils.readFileContent(filePath).trim().split(""))
                .map(d -> d.charAt(0))
                .map(this::hexToBin)
                .collect(Collectors.joining());

        var sumOfVersions = new AtomicInteger();
        var bitPointer = new AtomicInteger();
        decodePacket(bitPointer, sumOfVersions, binaryString);

        return sumOfVersions.get();
    }

    private Map<String, Object> decodePacket(AtomicInteger bitPointer, AtomicInteger sumOfVersions, String binaryString) {
        var version = Integer.parseInt(binaryString.substring(bitPointer.get(), bitPointer.get() + 3), 2);
        var typeId = Integer.parseInt(binaryString.substring(bitPointer.get() + 3, bitPointer.get() + 6), 2);

        bitPointer.addAndGet(6);
        sumOfVersions.addAndGet(version);

        return typeId == 4
                ? literalValue(bitPointer, binaryString)
                : operator(bitPointer, sumOfVersions, binaryString);
    }

    private Map<String, Object> operator(AtomicInteger bitPointer, AtomicInteger sumOfVersions, String binaryString) {
        var lengthTypeId = binaryString.charAt(bitPointer.get());

        if (lengthTypeId == '0') {
            var totalSubPacketLength = Integer.parseInt(binaryString.substring(bitPointer.get() + 1, bitPointer.get() + 16), 2);

            bitPointer.addAndGet(16);
            int preSubPacketsPosition = bitPointer.get();
            do {
                decodePacket(bitPointer, sumOfVersions, binaryString);
            } while (bitPointer.get() - preSubPacketsPosition < totalSubPacketLength);

        } else {
            var subPacketCount = Integer.parseInt(binaryString.substring(bitPointer.get() + 1, bitPointer.get() + 12), 2);
            bitPointer.addAndGet(12);

            for (int count = 0; count < subPacketCount; count++) {
                decodePacket(bitPointer, sumOfVersions, binaryString);
            }
        }

        return Collections.emptyMap();
    }

    private Map<String, Object> literalValue(AtomicInteger bitPointer, String binaryString) {
        var payload = new ArrayList<String>();

        checkPacket(
                bitPointer,
                binaryString,
                payload);

        return Map.of("literalValue", Long.valueOf(String.join("", payload), 2));
    }

    private void checkPacket(AtomicInteger bitPointer, String binaryString, ArrayList<String> payload) {
        var checkDigit = binaryString.charAt(bitPointer.get());
        var value = binaryString.substring(bitPointer.get() + 1, bitPointer.get() + 5);
        var isLastGroup = checkDigit == '0';

        payload.add(value);
        bitPointer.addAndGet(5);

        if (!isLastGroup) {
            checkPacket(bitPointer, binaryString, payload);
        }
    }

    private String hexToBin(char hex) {
        return switch (hex) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new RuntimeException();
        };
    }

    public long part2(String filePath) {
        return 0L;
    }
}
