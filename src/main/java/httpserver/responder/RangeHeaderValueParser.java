package httpserver.responder;

import httpserver.Range;

public class RangeHeaderValueParser {
    public Range parse(String headerValue, int payloadLength) {
        String[] parts = getParts(headerValue);
        String firstPart = parts[0];
        String secondPart = parts[1];

        if (firstPart.equals("")) {
            return getRangeAtEnd(payloadLength, secondPart);
        }

        if (secondPart.equals("")) {
            return getRangeAtStart(payloadLength, firstPart);
        }

        return getRange(parts);
    }

    private Range getRangeAtStart(int payloadLength, String firstPart) {
        int start = parseRangeValue(firstPart);
        return new Range(start, payloadLength - 1);
    }

    private Range getRangeAtEnd(int payloadLength, String secondPart) {
        int start = payloadLength - parseRangeValue(secondPart);
        return new Range(start, payloadLength - 1);
    }

    private Range getRange(String[] parts) {
        int start = parseRangeValue(parts[0]);
        int end = parseRangeValue(parts[1]);
        return new Range(start, end);
    }

    private String[] getParts(String headerValue) {
        String rangeString = headerValue.substring(6);
        return rangeString.split("-", 2);
    }

    private int parseRangeValue(String value) {
        return Integer.parseInt(value);
    }
}
