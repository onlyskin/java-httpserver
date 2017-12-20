package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CookieResponderTest {

    private final CookieResponder cookieResponder;

    public CookieResponderTest() {
        cookieResponder = new CookieResponder();
    }

    @Test
    public void hasCorrectPayloadAndSetsCookie() throws Exception {
        Response response = cookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(200, response.getStatusCode());
        assertEquals("Eat", new String(response.getPayload()));
        Header[] expected = new Header[]{new Header("Set-Cookie", "key=value")};
        assertTrue(Arrays.equals(expected, response.getHeaders()));
    }

    @Test
    public void handlesCookie() throws Exception {
        assertTrue(cookieResponder.handles("/cookie"));
        assertFalse(cookieResponder.handles("/other"));
    }
}
