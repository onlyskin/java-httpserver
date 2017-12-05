package httpserver.response;

import httpserver.response.MethodNotAllowedResponse;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class MethodNotAllowedResponseTest {
    @Test
    public void hasStatusCode405() throws Exception {
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse();
        assertEquals(405, methodNotAllowedResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse();
        assertTrue(Arrays.equals(new byte[0], methodNotAllowedResponse.getPayload()));
    }
}
