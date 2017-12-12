package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentLengthHeaderTest {
    @Test
    public void setsContentLength() throws Exception {
        byte[] payload = "test payload".getBytes();

        ContentLengthHeader contentLengthHeader = new ContentLengthHeader(payload);

        assertEquals(new Header("Content-Length", "12"), contentLengthHeader);
    }
}
