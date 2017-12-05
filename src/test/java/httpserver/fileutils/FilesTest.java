package httpserver.fileutils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static httpserver.fileutils.FileHelpers.tempDir;
import static httpserver.fileutils.FileHelpers.tempFile;
import static httpserver.fileutils.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static org.junit.Assert.*;

public class FilesTest {
    @Test
    public void makesFilePathFromRootPathAndRequestPathString() throws Exception {
        Path root = Paths.get("tmp/serving-dir");
        String requestPathString = "example.txt";

        Path expected = Paths.get("tmp/serving-dir/example.txt");
        assertEquals(expected,
                Files.getRequestPath(root, requestPathString));
    }

    @Test
    public void makesFilePathCorrectlyWhenRequestPathIsSlash() throws Exception {
        Path root = Paths.get("tmp/serving-dir");
        String requestPathString = "/";

        Path expected = Paths.get("tmp/serving-dir");
        assertEquals(expected,
                Files.getRequestPath(root, requestPathString));
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

    @Test
    public void checksPathExists() throws Exception {
        Path dir = tempDir();
        Path file = tempFile();
        Path nonePath = Paths.get("/var/nonePath");

        assertTrue(Files.pathExists(dir));
        assertTrue(Files.pathExists(file));
        assertFalse(Files.pathExists(nonePath));
    }

    @Test
    public void checksIfPathIsDir() throws Exception {
        Path dir = tempDir();
        Path file = tempFile();
        Path nonePath = Paths.get("/var/nonePath");

        assertTrue(Files.isDir(dir));
        assertFalse(Files.isDir(file));
        assertFalse(Files.isDir(nonePath));
    }

    @Test
    public void checksIfPathIsFile() throws Exception {
        Path dir = tempDir();
        Path file = tempFile();
        Path nonePath = Paths.get("/var/nonePath");

        assertFalse(Files.isFile(dir));
        assertTrue(Files.isFile(file));
        assertFalse(Files.isFile(nonePath));
    }

    @Test
    public void readsFileContentsToBytes() throws Exception {
        Path file = tempFile();
        write(file, "Temp file contents".getBytes());

        byte[] expected = "Temp file contents".getBytes();
        byte[] actual = Files.fileContents(file);
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void readsDirContentsToPathList() throws Exception {
        Path dir = tempDir();
        Path file1 = tempFileOptions(dir, "aaa");
        Path file2 = tempFileOptions(dir, "bbb");

        Path expected1 = file1;
        Path expected2 = file2;
        Path[] actual = Files.directoryContents(dir);
        assertEquals(expected1, actual[0]);
        assertEquals(expected2, actual[1]);
    }
}
