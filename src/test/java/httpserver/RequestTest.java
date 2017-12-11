package httpserver;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void hasCorrectMethodPathAndHeaders() throws Exception {
        Header[] headers = new Header[]{new Header("test-header", "test-value")};
        Request request = new Request("GET", "/example.txt", headers);

        assertEquals(Method.GET, request.getMethod());
        assertEquals("/example.txt", request.getPathString());

        Header[] expected = new Header[]{new Header("test-header", "test-value")};
        assertTrue(Arrays.equals(expected, request.getHeaders()));
    }

    @Test
    public void getsHeaderValue() throws Exception {
        Header[] headers = new Header[]{new Header("test-header", "test-value")};
        Request request = new Request("GET", "/example.txt", headers);

        assertEquals("test-value", request.getHeaderValue("test-header"));
    }

    @Test
    public void getsNullForEmptyHeader() throws Exception {
        Request request = new Request("GET", "/example.txt", new Header[0]);

        assertNull(request.getHeaderValue("test-header"));
    }

    @Test
    public void getMethodReturnsInvalidWhenBadMethod() throws Exception {
        Request request = new Request("ABCDEF", "/example.txt", new Header[0]);
        assertEquals(Method.INVALID, request.getMethod());
    }
}
