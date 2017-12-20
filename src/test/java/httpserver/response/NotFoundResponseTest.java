package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        OutputStream outputStreamMock = mock(OutputStream.class);
        notFoundResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write(new byte[0]);
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], notFoundResponse.getHeaders()));
    }
}
