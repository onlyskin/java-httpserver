package httpserver;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestParserTest {
    private final RequestParser requestParser;

    public RequestParserTest() {
        this.requestParser = new RequestParser();
    }

    @Test
    public void parsesMethod() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";

        Request request = requestForInput(input);

        assertEquals(Method.GET, request.getMethod());
    }

    @Test
    public void parsesPath() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";

        Request request = requestForInput(input);

        assertEquals("/text-file.txt", request.getPath());
    }

    @Test
    public void parsesHeaders() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";

        HashMap<String, String> headers = requestForInput(input).getHeaders();

        assertEquals(3, headers.size());
        assertEquals("0.0.0.0:5000", headers.get("Host"));
    }

    @Test
    public void parsesHeaderWithExtraWhitespace() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nExtra-space:   example\r\n\r\n";

        HashMap<String, String> headers = requestForInput(input).getHeaders();

        assertEquals(1, headers.size());
        assertEquals("example", headers.get("Extra-space"));
    }

    private Request requestForInput(String input) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return requestParser.parse(inputStream);
    }
}
