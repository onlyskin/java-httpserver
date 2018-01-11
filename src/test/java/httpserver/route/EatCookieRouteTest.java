package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EatCookieRouteTest {

    private final EatCookieRoute eatCookieResponder;

    public EatCookieRouteTest() {
        eatCookieResponder = new EatCookieRoute();
    }

    @Test
    public void hasCorrectPayloadAndSetsCookie() throws Exception {

        Response response = eatCookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(200, response.getStatusCode());
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("mmmm chocolate".getBytes());
    }

    @Test
    public void allowsEat_cookie() throws Exception {
        assertTrue(eatCookieResponder.allows(new Request(null, "/eat_cookie", null, null)));
        assertFalse(eatCookieResponder.allows(new Request(null, "/other", null, null)));
    }
}
