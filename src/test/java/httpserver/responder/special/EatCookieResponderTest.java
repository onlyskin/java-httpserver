package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.OutputStream;

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
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("mmmm chocolate".getBytes());
    }

    @Test
    public void allowsEat_cookie() throws Exception {
        assertTrue(eatCookieResponder.allows("/eat_cookie"));
        assertFalse(eatCookieResponder.allows("/other"));
    }
}
