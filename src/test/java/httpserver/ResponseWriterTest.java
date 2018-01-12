package httpserver;

import httpserver.header.Header;
import httpserver.response.TeapotResponse;
import httpserver.response.NotFoundResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponseWriterTest {
    private final ResponseWriter responseWriter;
    private final ByteArrayOutputStream outputStream;
    private final Logger logger;

    public ResponseWriterTest() {
        outputStream = new ByteArrayOutputStream();
        logger = mock(Logger.class);
        responseWriter = new ResponseWriter(outputStream, logger);
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
        Header header = new Header("example", "header");
        Response response = new OkResponse("example".getBytes());
        response.setHeader(header);

        String output = outputForResponse(response);

        assertTrue(output.contains("example: header"));
        assertTrue(output.contains("Content-Length: 7"));
    }

    @Test
    public void logsExceptionIfWritingThrowsException() throws Exception {
        OutputStream outputStreamMock = mock(OutputStream.class);
        doThrow(new IOException()).when(outputStreamMock).write(any());
        ResponseWriter responseWriter = new ResponseWriter(outputStreamMock, logger);
        Response response = new OkResponse("example".getBytes());

        responseWriter.write(response);

        verify(logger).log(any());
    }

    @Test
    public void itWritesTheFirstLineFor404() throws Exception {
        String output = outputForResponse(new NotFoundResponse());

        assertTrue(output.contains("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void itWritesTheFirstLineFor418() throws Exception {
        String output = outputForResponse(new TeapotResponse());

        assertTrue(output.contains("HTTP/1.1 418 I'm a teapot\r\n"));
    }

    private String outputForResponse(Response response) throws IOException {
        responseWriter.write(response);
        return outputStream.toString();
    }
}
