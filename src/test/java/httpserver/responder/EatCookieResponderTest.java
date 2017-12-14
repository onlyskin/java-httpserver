package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EatCookieResponderTest {

    private final EatCookieResponder eatCookieResponder;

    public EatCookieResponderTest() {
        eatCookieResponder = new EatCookieResponder();
    }

    @Test
    public void hasCorrectPayloadAndSetsCookie() throws Exception {

        Response response = eatCookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(200, response.getStatusCode());
        assertEquals("mmmm chocolate", new String(response.getPayload()));
    }

    @Test
    public void eat_cookieIsAllowed() throws Exception {
        assertTrue(eatCookieResponder.allowed("/eat_cookie"));
        assertFalse(eatCookieResponder.allowed("/other"));
    }
}
