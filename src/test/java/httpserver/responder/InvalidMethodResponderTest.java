package httpserver.responder;

import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidMethodResponderTest {
    @Test
    public void returns405Response() throws Exception {
        Response response = new InvalidMethodResponder().respond(null, null);

        assertEquals(405, response.getStatusCode());
    }
}
