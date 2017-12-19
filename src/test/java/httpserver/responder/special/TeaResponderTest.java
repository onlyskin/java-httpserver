package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.responder.special.TeaResponder;
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
    public void returns200() {
        Response response = teaResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void teaIsAllowed() throws Exception {
        assertTrue(teaResponder.allowed("/tea"));
        assertFalse(teaResponder.allowed("/other"));
    }
}
