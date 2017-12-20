package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnauthorizedResponseTest {
    private final UnauthorizedResponse unauthorizedResponse;

    public UnauthorizedResponseTest() {
        unauthorizedResponse = new UnauthorizedResponse();
    }

    @Test
    public void hasStatusCode405() throws Exception {
        assertEquals(401, unauthorizedResponse.getStatusCode());
    }

    @Test
    public void hasEmptyPayload() throws Exception {
        OutputStream outputStreamMock = mock(OutputStream.class);
        unauthorizedResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write(new byte[0]);
    }

    @Test
    public void hasWWWAuthenticateHeader() throws Exception {
        Header[] expected = new Header[]{new Header("WWW-Authenticate", "Basic realm=\"\"")};
        assertTrue(Arrays.equals(expected, unauthorizedResponse.getHeaders()));
    }
}
