package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RedirectResponderTest {

    private final RedirectResponder redirectResponder;

    public RedirectResponderTest() {
        redirectResponder = new RedirectResponder();
    }

    @Test
    public void returnsFourEighteen() throws Exception {
        Response response = redirectResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(302, response.getStatusCode());
        assertEquals("/", response.getHeaders()[0].getValue());
    }

    @Test
    public void handlesRedirect() throws Exception {
        assertTrue(redirectResponder.handles("/redirect"));
        assertFalse(redirectResponder.handles("/other"));
    }
}
