package httpserver;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void hasCorrectMethodPathAndHeaders() throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("test-header", "test-value");

        Request request = new Request("GET", "/example.txt", headers);
        assertEquals(Method.GET, request.getMethod());
        assertEquals("/example.txt", request.getPath());
        assertEquals("test-value", request.getHeaders().get("test-header"));
    }

    @Test
    public void getMethodReturnsInvalidWhenBadMethod() throws Exception {
        Request request = new Request("ABCDEF", "/example.txt", new HashMap<>());
        assertEquals(Method.INVALID, request.getMethod());
    }
}
