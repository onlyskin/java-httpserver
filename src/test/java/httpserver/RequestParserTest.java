package httpserver;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RequestParserTest {
    private final RequestParser requestParser;
    private final Logger loggerMock;

    public RequestParserTest() {
        this.loggerMock = mock(Logger.class);
        this.requestParser = new RequestParser(loggerMock);
    }

    @Test
    public void parsesMethodAndPath() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";

        Request request = requestForInput(input);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/text-file.txt", request.getPathString());
    }

    @Test
    public void parsesHeaders() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";

        Header[] actual = requestForInput(input).getHeaders();

        Header[] expected = new Header[]{new Header("Host", "0.0.0.0:5000"),
                new Header("User-Agent", "curl/7.54.0"),
                new Header("Accept", "*/*")};
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void parsesHeaderWithExtraWhitespace() throws Exception {
        String input = "GET /text-file.txt HTTP/1.1\r\nExtra-space:   example\r\n\r\n";

        Header[] actual = requestForInput(input).getHeaders();

        Header[] expected = new Header[]{new Header("Extra-space", "example")};
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void callsLogOnLogger() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET /text-file.txt HTTP/1.1\r\n\r\n".getBytes());
        requestParser.parse(inputStream);

        verify(loggerMock).log("GET /text-file.txt HTTP/1.1");
    }

    private Request requestForInput(String input) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return requestParser.parse(inputStream);
    }
}
