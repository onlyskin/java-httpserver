package httpserver.response;

import httpserver.response.NotFoundResponse;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class NotFoundResponseTest {

    private final NotFoundResponse notFoundResponse;

    public NotFoundResponseTest() {
        notFoundResponse = new NotFoundResponse();
    }

    @Test
    public void hasStatuscode404() throws Exception {
        assertEquals(404, notFoundResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        assertTrue(Arrays.equals(new byte[0], notFoundResponse.getPayload()));
    }
}
