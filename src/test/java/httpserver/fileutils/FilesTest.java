package httpserver.fileutils;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static httpserver.fileutils.FileHelpers.*;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.*;

public class FilesTest {

    private final Path root;
    private final Path dir;
    private final Path file1;
    private final Path file2;
    private final Path fileWithContents;
    private final Path nonePath;

    public FilesTest() throws IOException {
        root = Paths.get("tmp/serving-dir");
        dir = tempDir();
        file1 = tempFileOptions(dir, "aaa");
        file2 = tempFileOptions(dir, "bbb");
        fileWithContents = fileWithContents("Temp file contents");
        nonePath = Paths.get("/var/nonePath");
    }

    @Test
    public void checksPathExists() throws Exception {
        assertTrue(Files.pathExists(dir));
        assertTrue(Files.pathExists(file1));
        assertFalse(Files.pathExists(nonePath));
    }

    @Test
    public void checksIfPathIsDir() throws Exception {
        assertTrue(Files.isDir(dir));
        assertFalse(Files.isDir(file1));
        assertFalse(Files.isDir(nonePath));
    }

    @Test
    public void checksIfPathIsFile() throws Exception {
        assertFalse(Files.isFile(dir));
        assertTrue(Files.isFile(file1));
        assertFalse(Files.isFile(nonePath));
    }

    @Test
    public void readsFileContentsToBytes() throws Exception {
        byte[] expected = "Temp file contents".getBytes();
        byte[] actual = Files.fileContents(fileWithContents);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void readsDirContentsToPathList() throws Exception {
        Path[] actual = Files.directoryContents(dir);

        assertEquals(file1, actual[0]);
        assertEquals(file2, actual[1]);
    }

    @Test
    public void createsFileAtPath() throws Exception {
        Path path = Paths.get(dir.toString(), "/example.txt");
        Files.createFileAtPath(path);

        assertTrue(exists(path));
    }

    @Test
    public void deletesFileAtPath() throws Exception {
        Path path = Paths.get(dir.toString(), "/example.txt");
        Files.createFileAtPath(path);
        Files.deleteFileAtPath(path);

        assertFalse(exists(path));
    }

    @Test
    public void appendsToFileContentsAtPath() throws Exception {
        Path path = Paths.get(dir.toString(), "/example.txt");

        Files.createFileAtPath(path);
        String contents = new String(readAllBytes(path));
        assertEquals("", contents);

        Files.appendToFile(path, "first line\r\n".getBytes());
        String newContents1 = new String(readAllBytes(path));
        assertEquals("first line\r\n", newContents1);

        Files.appendToFile(path, "second line\r\n".getBytes());
        String newContents2 = new String(readAllBytes(path));
        assertEquals("first line\r\nsecond line\r\n", newContents2);
    }

    @Test
    public void replacesFileContentsAtPath() throws Exception {
        Path path = Paths.get(dir.toString(), "/example.txt");

        Files.createFileAtPath(path);
        String contents = new String(readAllBytes(path));
        assertEquals("", contents);

        Files.replaceContents(path, "first line\r\n".getBytes());
        String newContents1 = new String(readAllBytes(path));
        assertEquals("first line\r\n", newContents1);

        Files.replaceContents(path, "second line\r\n".getBytes());
        String newContents2 = new String(readAllBytes(path));
        assertEquals("second line\r\n", newContents2);
    }

    @Test
    public void makesFullPathFromRootPathAndRequestPathString() throws Exception {
        String requestPathString = "/example.txt";

        Path expected = Paths.get("tmp/serving-dir/example.txt");
        assertEquals(expected,
                Files.fullPathForRequestPath(root, requestPathString));
    }

    @Test
    public void makesFullPathCorrectlyWhenRequestPathIsSlash() throws Exception {
        String requestPathString = "/";

        Path expected = Paths.get("tmp/serving-dir");
        assertEquals(expected,
                Files.fullPathForRequestPath(root, requestPathString));
    }

    @Test
    public void makesRootPathFromStringInput() throws Exception {
        String input1 = "/Users/example/public/";
        String input2 = "/Users/example/public";

        assertEquals(Paths.get("/Users", "example", "public"),
                Files.getPath(input1));
        assertEquals(Paths.get("/Users", "example", "public"),
                Files.getPath(input2));
    }
}
