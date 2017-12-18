package httpserver.response;

import httpserver.header.Header;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class NoContentResponseTest {
    @Test
    public void hasStatusCode204AndEtagHeader() throws Exception {
        NoContentResponse noContentRespones = new NoContentResponse("bfc13a");

        Header[] expected = new Header[]{new Header("ETag", "bfc13a")};
        assertEquals(204, noContentRespones.getStatusCode());
        assertTrue(Arrays.equals(expected, noContentRespones.getHeaders()));
    }
}
