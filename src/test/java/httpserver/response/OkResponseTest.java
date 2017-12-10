package httpserver.response;

import httpserver.Header;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class OkResponseTest {
    private final OkResponse okResponse;

    public OkResponseTest() {
        Header header1 = new Header("header1", "value");
        Header header2 = new Header("header2", "v");
        Header[] headers = new Header[]{header1, header2};
        this.okResponse = new OkResponse("test payload".getBytes(), headers);
    }

    @Test
    public void getsStatusCode() throws Exception {
        assertEquals(200, okResponse.getStatusCode());
    }

    @Test
    public void getsPayload() throws Exception {
        byte[] payload = okResponse.getPayload();
        assertEquals("test payload", new String(payload));
    }

    @Test
    public void getsHeaders() throws Exception {
        Header[] expected = new Header[]{new Header("header1", "value"),
                new Header("header2", "v")};
        OkResponse okResponse = this.okResponse;
        assertTrue(Arrays.equals(expected, okResponse.getHeaders()));
    }

    @Test
    public void hasEmptyHeaderList() throws Exception {
        OkResponse okResponse = new OkResponse("test payload".getBytes());

        assertTrue(Arrays.equals(new Header[0], okResponse.getHeaders()));
    }
}
