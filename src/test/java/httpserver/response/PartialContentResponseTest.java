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
        assertEquals(new Header("Content-Length", "6"), partialContentResponse.getContentLengthHeader());
        assertEquals("ged te", new String(partialContentResponse.getPayload()));
    }
}
