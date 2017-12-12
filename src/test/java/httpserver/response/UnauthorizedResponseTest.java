package httpserver.response;

import httpserver.header.Header;
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
    public void hasWWWAuthenticateHeader() throws Exception {
        Header[] expected = new Header[]{new Header("WWW-Authenticate", "Basic realm=\"\"")};
        assertTrue(Arrays.equals(expected, unauthorizedResponse.getHeaders()));
    }
}
