package httpserver.response;

import httpserver.Header;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.*;

public class FourEighteenResponseTest {

    private final FourEighteenResponse fourEighteenResponse;

    public FourEighteenResponseTest() {
        fourEighteenResponse = new FourEighteenResponse();
    }

    @Test
    public void hasStatusCode405() throws Exception {
        assertEquals(418, fourEighteenResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        assertEquals("I'm a teapot", new String(fourEighteenResponse.getPayload()));
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], fourEighteenResponse.getHeaders()));
    }

}
