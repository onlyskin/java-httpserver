package httpserver;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestTest {

    private final Request request;

    public RequestTest() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("test-header", "test-value");

        this.request = new Request("GET", "example.txt", headers);
    }

    @Test
    public void getMethodReturnsGet() throws Exception {
        assertEquals(Method.GET, request.getMethod());
    }

    @Test
    public void getPathReturnsPath() throws Exception {
        assertEquals("example.txt", request.getPath());
    }

    @Test
    public void getHeadersReturnsHeaders() throws Exception {
        HashMap<String, String> headers = request.getHeaders();
        assertEquals("test-value", headers.get("test-header"));
    }
}
