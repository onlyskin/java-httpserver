package httpserver.response;

import httpserver.Range;
import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PartialContentResponseTest {
    @Test
    public void partialRange() throws Exception {
        PartialContentResponse partialContentResponse = new PartialContentResponse(new Range(3, 8), "ranged text".getBytes());
        assertEquals(206, partialContentResponse.getStatusCode());
        assertEquals(new Header("Content-Length", "6"), partialContentResponse.getContentLengthHeader());
        OutputStream outputStreamMock = mock(OutputStream.class);
        partialContentResponse.writePayload(outputStreamMock);
        verify(outputStreamMock, times(6)).write(anyInt());
    }
}
