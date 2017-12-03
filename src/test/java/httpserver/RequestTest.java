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
        assertEquals(request.getMethod(), "GET");
    }

    @Test
    public void getPathReturnsPath() throws Exception {
        assertEquals(request.getPath(), "example.txt");
    }

    @Test
    public void getHeadersReturnsHeaders() throws Exception {
        HashMap<String, String> headers = request.getHeaders();
        assertEquals(headers.get("test-header"), "test-value");
    }
}
