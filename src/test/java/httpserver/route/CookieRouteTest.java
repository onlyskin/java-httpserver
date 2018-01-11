package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CookieRouteTest {

    private final CookieRoute cookieResponder;

    public CookieRouteTest() {
        cookieResponder = new CookieRoute();
    }

    @Test
    public void hasCorrectPayloadAndSetsCookie() throws Exception {
        Response response = cookieResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(200, response.getStatusCode());
        Header[] expected = new Header[]{new Header("Set-Cookie", "key=value")};
        assertTrue(Arrays.equals(expected, response.getHeaders()));
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("Eat".getBytes());
    }

    @Test
    public void allowsCookie() throws Exception {
        assertTrue(cookieResponder.allows(new Request(null, "/cookie", null, null)));
        assertFalse(cookieResponder.allows(new Request(null, "/other", null, null)));
    }
}
