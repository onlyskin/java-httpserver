package httpserver;

import httpserver.header.Header;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RequestParserTest {
    private final RequestParser requestParser;
    private final InputStream inputStream;
    private final Logger loggerMock;

    public RequestParserTest() {
        AppConfig appConfigMock = mock(AppConfig.class);
        loggerMock = mock(Logger.class);
        when(appConfigMock.getLogger()).thenReturn(loggerMock);

        this.requestParser = new RequestParser(appConfigMock);

        String input = "GET /text-file?key1=value1%3C%2C%3F&key2=value2 HTTP/1.1\r\nHost: 0.0.0.0:5000\r\nUser-Agent: curl/7.54.0\r\nAccept: */*\r\n\r\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
    }

    @Test
    public void parsesMethodPathAndQueryString() throws Exception {
        Request request = requestParser.parse(inputStream);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/text-file", request.getPathString());
        assertEquals("key1=value1<,?&key2=value2", request.getQueryString());
    }

    @Test
    public void parsesHeadersIncludingWhitespace() throws Exception {
        Request request = requestParser.parse(inputStream);

        Header[] expected = new Header[]{new Header("Host", "0.0.0.0:5000"),
                new Header("User-Agent", "curl/7.54.0"),
                new Header("Accept", "*/*")};
        Header[] actual = request.getHeaders();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void callsLogOnLogger() throws Exception {
        requestParser.parse(inputStream);

        verify(loggerMock).log("GET /text-file?key1=value1%3C%2C%3F&key2=value2 HTTP/1.1");
    }

}
