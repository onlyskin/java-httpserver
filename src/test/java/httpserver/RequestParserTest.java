package httpserver;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class RequestParserTest {
    @Test
    public void itMakesGetRequest() throws Exception {
        String input = "GET /docs/index.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        RequestParser requestParser = new RequestParser();

        Request request = requestParser.parse(inputStream);

        assertEquals(request.getMethod(), Method.GET);
        assertEquals(request.getPath(), "/docs/index.html");
    }
}
