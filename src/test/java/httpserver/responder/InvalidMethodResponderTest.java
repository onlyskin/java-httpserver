package httpserver.responder;

import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidMethodResponderTest {
    @Test
    public void returns405Response() throws Exception {
        InvalidMethodResponder invalidMethodResponder = new InvalidMethodResponder();

        Response response = invalidMethodResponder.respond(null, null);
        assertEquals(405, response.getStatusCode());
    }
}
