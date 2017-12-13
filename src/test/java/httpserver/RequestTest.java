package httpserver;

import httpserver.header.Header;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

public class RequestTest {

    private final Header[] headers;

    public RequestTest() {
        headers = new Header[]{new Header("test-header", "test-value")};
    }

    @Test
    public void hasCorrectMethodPathHeadersAndQueryString() throws Exception {
        Request request = new Request("GET", "/example.txt", headers, "example=test");

        assertEquals("GET", request.getMethodString());
        assertEquals("/example.txt", request.getPathString());

        Header[] expected = new Header[]{new Header("test-header", "test-value")};
        assertTrue(Arrays.equals(expected, request.getHeaders()));
        assertEquals("example=test", request.getQueryString());
    }

    @Test
    public void canGetHeaderValue() throws Exception {
        Request request = new Request("GET", "/example.txt", headers, "");

        String headerValue = request.getHeaderValue("test-header");

        assertEquals("test-value", headerValue);
    }

    @Test
    public void testsIfHeaderPresent() throws Exception {
        Request request = new Request("GET", "/example.txt", headers, "");

        assertTrue(request.hasHeader("test-header"));
        assertFalse(request.hasHeader("no-header"));
    }

    @Test
    public void getsNullForEmptyHeader() throws Exception {
        Request request = new Request("GET", "/example.txt", new Header[0], "");

        assertNull(request.getHeaderValue("test-header"));
    }

    @Test
    public void getsEmptyStringForNoQueryString() throws Exception {
        Request request = new Request("GET", "/example.txt", headers, "");

        assertEquals("", request.getQueryString());
    }

    @Test
    public void getsParams() throws Exception {
        Request request = new Request("GET", "/example", new Header[0], "key1=value1%3C%2C%3F&key2=value2");

        Parameter[] expected = new Parameter[]{new Parameter("key1", "value1<,?"), new Parameter("key2", "value2")};
        Parameter[] actual = request.getParams();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void getsBody() throws Exception {
        Request request = new Request("GET", "/example", new Header[0], "", "body content");

        assertEquals("body content", request.getBody());
    }
}
