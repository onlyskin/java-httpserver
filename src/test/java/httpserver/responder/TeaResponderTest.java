package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TeaResponderTest {
    @Test
    public void returns200() {
        TeaResponder teaResponder = new TeaResponder();

        Response response = teaResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(200, response.getStatusCode());
    }
}
