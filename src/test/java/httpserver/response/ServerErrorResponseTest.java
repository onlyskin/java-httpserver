package httpserver.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServerErrorResponseTest {
    @Test
    public void has500ResponseCode() throws Exception {
        ServerErrorResponse serverErrorResponse = new ServerErrorResponse();

        assertEquals(500, serverErrorResponse.getStatusCode());
    }
}
