package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoundResponseTest {
    @Test
    public void hasStatusCode302() throws Exception {
        FoundResponse foundResponse = new FoundResponse("example");

        Header[] expected = new Header[]{new Header("Location", "example")};
        assertEquals(302, foundResponse.getStatusCode());
        assertEquals(expected, foundResponse.getHeaders());
    }
}
