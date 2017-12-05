package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class OkResponseTest {
    private final byte[] payload;
    private final OkResponse okResponse;

    public OkResponseTest() {
        this.payload = "test payload".getBytes();
        this.okResponse = new OkResponse(payload);
    }

    @Test
    public void getStatusCodeReturns200() throws Exception {
        assertEquals(200, okResponse.getStatusCode());
    }

    @Test
    public void getPayloadReturnsPayload() throws Exception {
        byte[] payload = okResponse.getPayload();
        assertEquals("test payload", new String(payload));
    }
}
