package httpserver.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class OkResponseTest {
    private final OkResponse okResponse;

    public OkResponseTest() {
        this.okResponse = new OkResponse("test payload".getBytes());
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
