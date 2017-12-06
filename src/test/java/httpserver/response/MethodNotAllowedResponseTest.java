package httpserver.response;

import httpserver.response.MethodNotAllowedResponse;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class MethodNotAllowedResponseTest {

    private final MethodNotAllowedResponse methodNotAllowedResponse;

    public MethodNotAllowedResponseTest() {
        methodNotAllowedResponse = new MethodNotAllowedResponse();
    }

    @Test
    public void hasStatusCode405() throws Exception {
        assertEquals(405, methodNotAllowedResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        assertTrue(Arrays.equals(new byte[0], methodNotAllowedResponse.getPayload()));
    }
}
