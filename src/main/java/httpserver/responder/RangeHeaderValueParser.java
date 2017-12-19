package httpserver.responder;

import httpserver.Range;

public class RangeHeaderValueParser {
    public Range parse(String headerValue) {
        String rangeString = headerValue.substring(6);
        String[] parts = rangeString.split("-", 2);
        int start = parseRangeValue(parts[0], 0);
        int end = parseRangeValue(parts[1], Integer.MAX_VALUE);
        return new Range(start, end);
    }

    private int parseRangeValue(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
