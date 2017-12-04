package httpserver;

import org.junit.Test;

import java.io.FileOutputStream;
import java.nio.file.Path;

import static java.nio.file.Files.createTempFile;
import static org.junit.Assert.*;

public class FileWrapperTest {
    @Test
    public void getsFileContents() throws Exception {
        Path tempFile1 = createTempFile("aaa", "-testfile");
        tempFile1.toFile().deleteOnExit();
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile1.toString());
        fileOutputStream.write("Test file contents.\nLineTwo".getBytes());

        FileWrapper fileWrapper = new FileWrapper(tempFile1.toString());

        assertEquals("Test file contents.\nLineTwo", new String(fileWrapper.contents()));
    }
}
