package httpserver.response;

import httpserver.Header;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class UnauthorizedResponseTest {
    private final UnauthorizedResponse unauthorizedResponse;

    public UnauthorizedResponseTest() {
        unauthorizedResponse = new UnauthorizedResponse();
    }

    @Test
    public void hasStatusCode405() throws Exception {
        assertEquals(401, unauthorizedResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        assertTrue(Arrays.equals(new byte[0], unauthorizedResponse.getPayload()));
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], unauthorizedResponse.getHeaders()));
    }
}
