package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {
    private final byte[] payload;
    private final Response response;

    public ResponseTest() {
        this.payload = "test payload".getBytes();
        this.response = new Response(200, payload);
    }

    @Test
    public void getStatusCodeReturns200() throws Exception {
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void getPayloadReturnsPayload() throws Exception {
        byte[] payload = response.getPayload();
        assertEquals("test payload", new String(payload));
    }
}
