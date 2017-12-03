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
        String output = outputStreamForResponse(200, "example");

        assertEquals(output,
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 7\r\n" +
                        "\r\n" +
                        "example");
    }

    @Test
    public void itWritesTheFirstLineFor404() throws Exception {
        String output = outputStreamForResponse(404, "");

        assertTrue(output.contains("HTTP/1.1 404 Not Found\r\n"));
    }

    public String outputStreamForResponse(int statusCode, String payload) throws IOException {
        Response response = new Response(statusCode, payload.getBytes());
        responseWriter.write(response, outputStream);

        return outputStream.toString();
    }
}
