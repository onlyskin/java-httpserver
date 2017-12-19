package httpserver.responder;

import httpserver.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeHeaderValueParserTest {

    private final RangeHeaderValueParser rangeHeaderValueParser;

    public RangeHeaderValueParserTest() {
        rangeHeaderValueParser = new RangeHeaderValueParser();
    }

    @Test
    public void parsesSimpleRange() throws Exception {
        String headerValue = "bytes=6-10";

        Range range = rangeHeaderValueParser.parse(headerValue);

        assertEquals(6, range.getStart());
        assertEquals(10, range.getEnd());
    }

    @Test
    public void parsesRangeWithEmptyStart() throws Exception {
        String headerValue = "bytes=-10";

        Range range = rangeHeaderValueParser.parse(headerValue);

        assertEquals(0, range.getStart());
        assertEquals(10, range.getEnd());
    }

    @Test
    public void rangeWithEmptyEndHasMaxIntegerEndValue() throws Exception {
        String headerValue = "bytes=8-";

        Range range = rangeHeaderValueParser.parse(headerValue);

        assertEquals(8, range.getStart());
        assertEquals(Integer.MAX_VALUE, range.getEnd());
    }
}
