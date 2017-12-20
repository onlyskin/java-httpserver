package httpserver.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class BadRequestResponseTest {
    @Test
    public void has400StatusCode() throws Exception {
        BadRequestResponse badRequestResponse = new BadRequestResponse();

        assertEquals(400, badRequestResponse.getStatusCode());
    }
}
