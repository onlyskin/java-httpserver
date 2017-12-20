package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TeaResponderTest {

    private final TeaResponder teaResponder;

    public TeaResponderTest() {
        teaResponder = new TeaResponder();
    }

    @Test
    public void returns200() throws Exception {
        Response response = teaResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void handlesTea() throws Exception {
        assertTrue(teaResponder.handles("/tea"));
        assertFalse(teaResponder.handles("/other"));
    }
}