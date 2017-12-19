package httpserver.response;

import httpserver.Range;
import httpserver.header.Header;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartialContentResponseTest {
    @Test
    public void partialRange() throws Exception {
        PartialContentResponse partialContentResponse = new PartialContentResponse(new Range(3, 8), "ranged text".getBytes());
        assertEquals(206, partialContentResponse.getStatusCode());
        assertEquals(new Header("Content-Length", "5"), partialContentResponse.getContentLengthHeader());
        assertEquals("ged t", new String(partialContentResponse.getPayload()));
    }

    @Test
    public void partialRangeWithHighEnd() throws Exception {
        PartialContentResponse partialContentResponse = new PartialContentResponse(new Range(3, 10000), "ranged text".getBytes());
        assertEquals("ged text", new String(partialContentResponse.getPayload()));
    }
}
