package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        OutputStream outputStreamMock = mock(OutputStream.class);
        methodNotAllowedResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write(new byte[0]);
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], methodNotAllowedResponse.getHeaders()));
    }
}
