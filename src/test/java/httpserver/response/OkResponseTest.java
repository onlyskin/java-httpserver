package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OkResponseTest {
    private final OkResponse okResponse;

    public OkResponseTest() {
        Header header1 = new Header("header1", "value");
        Header header2 = new Header("header2", "v");
        this.okResponse = new OkResponse("test payload".getBytes());
        okResponse.setHeader(header1);
        okResponse.setHeader(header2);
    }

    @Test
    public void getsStatusCode() throws Exception {
        assertEquals(200, okResponse.getStatusCode());
    }

    @Test
    public void writesPayloadToOutputStream() throws Exception {
        OutputStream outputStreamMock = mock(OutputStream.class);

        okResponse.writePayload(outputStreamMock);

        verify(outputStreamMock).write("test payload".getBytes());
    }

    @Test
    public void doesntWritePayloadToOutputStreamWhenHEAD() throws Exception {
        OutputStream outputStreamMock = mock(OutputStream.class);
        okResponse.setHeadTrue();

        okResponse.writePayload(outputStreamMock);

        verify(outputStreamMock).write("".getBytes());
    }

    @Test
    public void getsHeaders() throws Exception {
        Header[] expected = new Header[]{new Header("header1", "value"),
                new Header("header2", "v")};

        assertTrue(Arrays.equals(expected, okResponse.getHeaders()));
    }

    @Test
    public void getsContentLengthHeader() throws Exception {
        Header expected = new Header("Content-Length", "12");

        assertEquals(expected, okResponse.getContentLengthHeader());
    }

    @Test
    public void hasEmptyHeaderList() throws Exception {
        OkResponse okResponse = new OkResponse("test payload".getBytes());

        assertTrue(Arrays.equals(new Header[0], okResponse.getHeaders()));
    }

    @Test
    public void setsHeadToTrue() throws Exception {
        OkResponse okResponse = new OkResponse("test payload".getBytes());

        OutputStream outputStreamMock = mock(OutputStream.class);
        okResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write("test payload".getBytes());

        okResponse.setHeadTrue();

        okResponse.writePayload(outputStreamMock);
        verify(outputStreamMock).write("".getBytes());
    }
}
