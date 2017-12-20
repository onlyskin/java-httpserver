package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class TeapotResponseTest {

    private final TeapotResponse teapotResponse;

    public TeapotResponseTest() {
        teapotResponse = new TeapotResponse();
    }

    @Test
    public void hasStatusCode405() throws Exception {
        assertEquals(418, teapotResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        assertEquals("I'm a teapot", new String(teapotResponse.getPayload()));
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], teapotResponse.getHeaders()));
    }

}
