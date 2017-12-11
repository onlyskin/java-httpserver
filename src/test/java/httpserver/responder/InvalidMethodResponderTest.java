package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InvalidMethodResponderTest {
    @Test
    public void returns405Response() throws Exception {
        Response response = new InvalidMethodResponder().
                respond(mock(AppConfig.class), mock(Request.class));

        assertEquals(405, response.getStatusCode());
    }
}
