package httpserver;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ContentTypeHeaderTest {
    @Test
    public void hasContentTypeText() throws Exception {
        Path path = Paths.get("home", "example.txt");
        ContentTypeHeader contentTypeHeader = new ContentTypeHeader(path);

        Header expected = new Header("Content-Type", "text/plain");
        assertEquals(expected, contentTypeHeader);
    }
}
