package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EatCookieResponderTest {
    @Test
    public void hasCorrectPayloadAndSetsCookie() throws Exception {
        EatCookieResponder eatCookieResponder = new EatCookieResponder();

        Response response = eatCookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(200, response.getStatusCode());
        assertEquals("mmmm chocolate", new String(response.getPayload()));
    }
}
