package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RedirectRouteTest {

    private final RedirectRoute redirectResponder;

    public RedirectRouteTest() {
        redirectResponder = new RedirectRoute();
    }

    @Test
    public void returnsFourEighteen() throws Exception {
        Response response = redirectResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(302, response.getStatusCode());
        assertEquals("/", response.getHeaders()[0].getValue());
    }

    @Test
    public void allowsRedirect() throws Exception {
        assertTrue(redirectResponder.allows(new Request(null, "/redirect", null, null)));
        assertFalse(redirectResponder.allows(new Request(null, "/other", null, null)));
    }
}
