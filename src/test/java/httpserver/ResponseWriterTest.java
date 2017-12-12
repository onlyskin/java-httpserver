package httpserver;

import httpserver.response.FourEighteenResponse;
import httpserver.response.NotFoundResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseWriterTest {
    private final ResponseWriter responseWriter;
    private final ByteArrayOutputStream outputStream;

    public ResponseWriterTest() {
        this.outputStream = new ByteArrayOutputStream();
        this.responseWriter = new ResponseWriter(outputStream);
    }

    @Test
    public void writes200RequestWithPayload() throws Exception {
        String output = outputForResponse(new OkResponse("example".getBytes()));

        assertEquals(output,
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 7\r\n" +
                        "\r\n" +
                        "example");
    }

    @Test
    public void writesResponseHeaders() throws Exception {
        Header headerMock = mock(Header.class);
        when(headerMock.toString()).thenReturn("example: header");
        Response response = new OkResponse("example".getBytes());
        response.setHeader(headerMock);

        String output = outputForResponse(response);

        assertTrue(output.contains("example: header"));
    }

    @Test
    public void itWritesTheFirstLineFor404() throws Exception {
        String output = outputForResponse(new NotFoundResponse());

        assertTrue(output.contains("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void itWritesTheFirstLineFor418() throws Exception {
        String output = outputForResponse(new FourEighteenResponse());

        assertTrue(output.contains("HTTP/1.1 418 I'm a teapot\r\n"));
    }

    private String outputForResponse(Response response) throws IOException {
        responseWriter.write(response);
        return outputStream.toString();
    }
}
