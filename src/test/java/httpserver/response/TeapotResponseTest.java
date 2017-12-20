package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        OutputStream outputStreamMock = mock(OutputStream.class);
        teapotResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write("I'm a teapot".getBytes());
    }

    @Test
    public void hasNoHeaders() throws Exception {
        assertTrue(Arrays.equals(new Header[0], teapotResponse.getHeaders()));
    }

}
