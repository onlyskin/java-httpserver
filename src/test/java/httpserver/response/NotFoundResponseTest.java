package httpserver.response;

import httpserver.response.NotFoundResponse;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class NotFoundResponseTest {
    @Test
    public void hasStatuscode404() throws Exception {
        NotFoundResponse notFoundResponse = new NotFoundResponse();
        assertEquals(404, notFoundResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        NotFoundResponse notFoundResponse = new NotFoundResponse();
        assertTrue(Arrays.equals(new byte[0], notFoundResponse.getPayload()));
    }
}
