package httpserver.header;

import httpserver.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeHeaderValueParserTest {

    private final RangeHeaderValueParser rangeHeaderValueParser;
    private final int payloadLength;

    public RangeHeaderValueParserTest() {
        rangeHeaderValueParser = new RangeHeaderValueParser();
        payloadLength = 10;
    }

    @Test
    public void parsesSimpleRange() throws Exception {
        String headerValue = "bytes=2-3";

        Range range = rangeHeaderValueParser.parse(headerValue, payloadLength);

        assertEquals(2, range.getStart());
        assertEquals(3, range.getEnd());
    }

    @Test
    public void parsesRangeWithEmptyStart() throws Exception {
        String headerValue = "bytes=-3";

        Range range = rangeHeaderValueParser.parse(headerValue, payloadLength);

        assertEquals(7, range.getStart());
        assertEquals(9, range.getEnd());
    }

    @Test
    public void rangeWithEmptyEndHasMaxIntegerEndValue() throws Exception {
        String headerValue = "bytes=3-";

        Range range = rangeHeaderValueParser.parse(headerValue, payloadLength);

        assertEquals(3, range.getStart());
        assertEquals(9, range.getEnd());
    }
}
