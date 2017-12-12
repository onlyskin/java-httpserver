package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CookieResponderTest {
    @Test
    public void returnsFourEighteen() throws Exception {
        CookieResponder cookieResponder = new CookieResponder();

        Response response = cookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(200, response.getStatusCode());
        assertEquals("Eat", new String(response.getPayload()));
    }
}
