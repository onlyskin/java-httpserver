package httpserver.file;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MimetypeCheckerTest {

    private final MimetypeChecker mimetypeChecker;

    public MimetypeCheckerTest() {
        mimetypeChecker = new MimetypeChecker();
    }

    @Test
    public void returnsTypeForJpegPath() throws Exception {
        Path path = Paths.get("/test/example.jpeg");

        assertEquals("image/jpeg", mimetypeChecker.getMimeType(path));
    }

    @Test
    public void returnsTypeForPngPath() throws Exception {
        Path path = Paths.get("/test/example.png");

        assertEquals("image/png", mimetypeChecker.getMimeType(path));
    }

    @Test
    public void returnsTypeForGifPath() throws Exception {
        Path path = Paths.get("/test/example.gif");

        assertEquals("image/gif", mimetypeChecker.getMimeType(path));
    }

    @Test
    public void returnsTypeForTxtPath() throws Exception {
        Path path = Paths.get("/test/example.txt");

        assertEquals("text/plain", mimetypeChecker.getMimeType(path));
    }
}
