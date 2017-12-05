package httpserver.fileutils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
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
        Path dir = createTempDirectory("dir");
        dir.toFile().deleteOnExit();
        Path file = createTempFile("file", "temp");
        file.toFile().deleteOnExit();
        Path nonePath = Paths.get("/var/nonePath");

        assertTrue(Files.pathExists(dir));
        assertTrue(Files.pathExists(file));
        assertFalse(Files.pathExists(nonePath));
    }

    @Test
    public void checksIfPathIsDir() throws Exception {
        Path dir = createTempDirectory("dir");
        dir.toFile().deleteOnExit();
        Path file = createTempFile("file", "temp");
        file.toFile().deleteOnExit();
        Path nonePath = Paths.get("/var/nonePath");

        assertTrue(Files.isDir(dir));
        assertFalse(Files.isDir(file));
        assertFalse(Files.isDir(nonePath));
    }

    @Test
    public void checksIfPathIsFile() throws Exception {
        Path dir = createTempDirectory("dir");
        dir.toFile().deleteOnExit();
        Path file = createTempFile("file", "temp");
        file.toFile().deleteOnExit();
        Path nonePath = Paths.get("/var/nonePath");

        assertFalse(Files.isFile(dir));
        assertTrue(Files.isFile(file));
        assertFalse(Files.isFile(nonePath));
    }
}
