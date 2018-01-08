package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class InvalidMethodResponderTest {

    private final InvalidMethodResponder invalidMethodResponder;

    public InvalidMethodResponderTest() {
        invalidMethodResponder = new InvalidMethodResponder();
    }

    @Test
    public void returns405Response() throws Exception {
        Response response = invalidMethodResponder.
                respond(mock(AppConfig.class), mock(Request.class));

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void allowsAllPaths() throws Exception {
        assertTrue(invalidMethodResponder.allows("/logs"));
        assertTrue(invalidMethodResponder.allows("/other"));
    }

    @Test
    public void handlesAllRequests() throws Exception {
        Request request = new Request("XYVABC", null, null, null);
        assertTrue(invalidMethodResponder.handles(request));
    }
}
