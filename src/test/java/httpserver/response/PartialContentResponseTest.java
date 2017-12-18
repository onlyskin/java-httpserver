package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartialContentResponseTest {

    private final PartialContentResponse partialContentResponse;

    public PartialContentResponseTest() {
        partialContentResponse = new PartialContentResponse(3, 8, "ranged text".getBytes());
    }

    @Test
    public void hasStatusCode206() throws Exception {
        assertEquals(206, partialContentResponse.getStatusCode());
    }

    @Test
    public void getPayloadReturnsRangeOfPayload() throws Exception {
        assertEquals("ged t", new String(partialContentResponse.getPayload()));
    }

    @Test
    public void getsContentLengthHeader() throws Exception {
        assertEquals(new Header("Content-Length", "5"), partialContentResponse.getContentLengthHeader());
    }
}
