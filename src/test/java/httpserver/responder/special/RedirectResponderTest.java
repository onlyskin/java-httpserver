package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.responder.special.RedirectResponder;
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
    public void redirectIsAllowed() throws Exception {
        assertTrue(redirectResponder.allowed("/redirect"));
        assertFalse(redirectResponder.allowed("/other"));
    }
}
