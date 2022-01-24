package com.softhale.day16;

import com.softhale.utils.ParserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {

    private final ParserUtils parserUtils = new ParserUtils();
    private final AtomicLong versionSum = new AtomicLong();

    public Long part1(String filePath) {
        decode(filePath);
        return versionSum.get();
    }

    public Long part2(String filePath) {
        return decode(filePath);
    }

    private Long decode(String filePath) {
        var binaryString = Stream.of(parserUtils.readFileContent(filePath).trim().split(""))
                .map(d -> d.charAt(0))
                .map(this::hexToBin)
                .collect(Collectors.joining());

        return decodePacket(new AtomicInteger(), binaryString);
    }

    private Long decodePacket(AtomicInteger bitPointer, String binaryString) {
        var version = Integer.parseInt(binaryString.substring(bitPointer.get(), bitPointer.get() + 3), 2);
        var typeId = Integer.parseInt(binaryString.substring(bitPointer.get() + 3, bitPointer.get() + 6), 2);

        versionSum.addAndGet(version);
        bitPointer.addAndGet(6);

        return typeId == 4
                ? literalValue(bitPointer, binaryString)
                : operator(typeId, bitPointer, binaryString);
    }

    private Long operator(int typeId, AtomicInteger bitPointer, String binaryString) {
        var lengthTypeId = binaryString.charAt(bitPointer.get());
        bitPointer.addAndGet(1);

        var decodedValues = new ArrayList<Long>();

        if (lengthTypeId == '0') {
            var totalSubPacketLength = Integer.parseInt(binaryString.substring(bitPointer.get(), bitPointer.get() + 15), 2);

            bitPointer.addAndGet(15);
            int preSubPacketsPosition = bitPointer.get();
            do {
                var decodedValue = decodePacket(bitPointer, binaryString);
                decodedValues.add(decodedValue);
            } while (bitPointer.get() - preSubPacketsPosition < totalSubPacketLength);

        } else {
            var subPacketCount = Integer.parseInt(binaryString.substring(bitPointer.get(), bitPointer.get() + 11), 2);
            bitPointer.addAndGet(11);

            for (int count = 0; count < subPacketCount; count++) {
                var decodedValue = decodePacket(bitPointer, binaryString);
                decodedValues.add(decodedValue);
            }
        }

        return handleTypeIds(typeId, decodedValues);
    }

    private Long handleTypeIds(int typeId, List<Long> values) {
        return switch (typeId) {
            case 0 -> values.stream().reduce(Long::sum).orElseThrow();
            case 1 -> values.stream().reduce((l1, l2) -> l1 * l2).orElseThrow();
            case 2 -> values.stream().min(Long::compareTo).orElseThrow();
            case 3 -> values.stream().max(Long::compareTo).orElseThrow();
            case 5 -> values.get(0) > values.get(1) ? 1L : 0L;
            case 6 -> values.get(0) < values.get(1) ? 1L : 0L;
            case 7 -> values.get(0).equals(values.get(1)) ? 1L : 0L;
            default -> throw new IllegalStateException("Unexpected value: " + typeId);
        };
    }

    private Long literalValue(AtomicInteger bitPointer, String binaryString) {
        var payload = new ArrayList<String>();

        checkPacket(
                bitPointer,
                binaryString,
                payload);

        return Long.valueOf(String.join("", payload), 2);
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
}
