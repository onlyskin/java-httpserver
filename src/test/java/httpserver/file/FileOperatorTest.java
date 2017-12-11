package httpserver.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.file.FileHelpers.tempDir;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.*;

public class FileOperatorTest {

    private final Path path;
    private FileOperator fileOperator;

    public FileOperatorTest() throws IOException {
        Path dir = tempDir();
        path = Paths.get(dir.toString(), "/example.txt");
        fileOperator = new FileOperator();
    }

    @Test
    public void createsFileAtPath() throws Exception {
        fileOperator.createFileAtPath(path);

        assertTrue(exists(path));
    }

    @Test
    public void deletesFileAtPath() throws Exception {
        fileOperator.createFileAtPath(path);
        fileOperator.deleteFileAtPath(path);

        assertFalse(exists(path));
    }

    @Test
    public void appendsToFileContentsAtPath() throws Exception {
        fileOperator.createFileAtPath(path);
        assertEquals("", fileContents(path));

        fileOperator.appendToFile(path, "first line\r\n".getBytes());
        assertEquals("first line\r\n",
                fileContents(path));

        fileOperator.appendToFile(path, "second line\r\n".getBytes());
        assertEquals("first line\r\nsecond line\r\n",
                fileContents(path));
    }

    @Test
    public void replacesFileContentsAtPath() throws Exception {
        fileOperator.createFileAtPath(path);
        assertEquals("", fileContents(path));

        fileOperator.replaceContents(path, "first line\r\n".getBytes());
        assertEquals("first line\r\n", fileContents(path));

        fileOperator.replaceContents(path, "second line\r\n".getBytes());
        assertEquals("second line\r\n", fileContents(path));
    }

    @Test
    public void readsFileContentsAtPath() throws Exception {
        fileOperator.createFileAtPath(path);
        fileOperator.appendToFile(path, "first line\r\n".getBytes());

        String contentsAsString = new String(fileOperator.readContents(path));
        assertEquals("first line\r\n", contentsAsString);
    }

    private String fileContents(Path path) throws IOException {
        return new String(readAllBytes(path));
    }
}
