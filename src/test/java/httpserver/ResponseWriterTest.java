package httpserver;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseWriterTest {
    private final ResponseWriter responseWriter;
    private final ByteArrayOutputStream outputStream;

    public ResponseWriterTest() {
        this.responseWriter = new ResponseWriter();
        this.outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void writes200RequestWithPayload() throws Exception {
        OkResponse okResponse = new OkResponse("example".getBytes());
        responseWriter.write(okResponse, outputStream);
        String output = outputStream.toString();

        assertEquals(output,
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 7\r\n" +
                        "\r\n" +
                        "example");
    }

    @Test
    public void itWritesTheFirstLineFor404() throws Exception {
        NotFoundResponse notFoundResponse = new NotFoundResponse();
        responseWriter.write(notFoundResponse, outputStream);
        String output = outputStream.toString();

        assertTrue(output.contains("HTTP/1.1 404 Not Found\r\n"));
    }
}
