package httpserver.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static httpserver.file.FileHelpers.*;
import static org.junit.Assert.*;

public class PathExaminerTest {

    private final Path root;
    private final Path dir;
    private final Path file1;
    private final Path file2;
    private final Path fileWithContents;
    private final Path nonePath;
    private final PathExaminer pathExaminer;

    public PathExaminerTest() throws IOException {
        pathExaminer = new PathExaminer();
        root = Paths.get("tmp/serving-dir");
        dir = tempDir();
        file1 = tempFileOptions(dir, "aaa", "temp");
        file2 = tempFileOptions(dir, "bbb", "temp");
        fileWithContents = fileWithContents("Temp file contents");
        nonePath = Paths.get("/var/nonePath");
    }

    @Test
    public void checksPathExists() throws Exception {
        assertTrue(pathExaminer.pathExists(dir));
        assertTrue(pathExaminer.pathExists(file1));
        assertFalse(pathExaminer.pathExists(nonePath));
    }

    @Test
    public void checksIfPathIsDir() throws Exception {
        assertTrue(pathExaminer.isDir(dir));
        assertFalse(pathExaminer.isDir(file1));
        assertFalse(pathExaminer.isDir(nonePath));
    }

    @Test
    public void checksIfPathIsFile() throws Exception {
        assertFalse(pathExaminer.isFile(dir));
        assertTrue(pathExaminer.isFile(file1));
        assertFalse(pathExaminer.isFile(nonePath));
    }

    @Test
    public void readsFileContentsToBytes() throws Exception {
        byte[] expected = "Temp file contents".getBytes();
        byte[] actual = pathExaminer.fileContents(fileWithContents);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void readsDirContentsToPathList() throws Exception {
        Path[] actual = pathExaminer.directoryContents(dir);

        assertEquals(file1, actual[0]);
        assertEquals(file2, actual[1]);
    }

    @Test
    public void makesFullPathFromRootPathAndRequestPathString() throws Exception {
        String requestPath= "/example.txt";

        Path expected = Paths.get("tmp/serving-dir/example.txt");
        assertEquals(expected,
                pathExaminer.getFullPath(root, requestPath));
    }

    @Test
    public void makesFullPathCorrectlyWhenRequestPathIsSlash() throws Exception {
        String requestPath= "/";

        Path expected = Paths.get("tmp/serving-dir");
        assertEquals(expected,
                pathExaminer.getFullPath(root, requestPath));
    }

    @Test
    public void makesRootPathFromStringInput() throws Exception {
        String input1 = "/Users/example/public/";
        String input2 = "/Users/example/public";

        assertEquals(Paths.get("/Users", "example", "public"),
                pathExaminer.getPath(input1));
        assertEquals(Paths.get("/Users", "example", "public"),
                pathExaminer.getPath(input2));
    }

    @Test
    public void concatenatesTwoPaths() throws Exception {
        Path path = Paths.get("/example", "test");
        String suffix = "logs";

        String expected = "/example/test/logs";
        assertEquals(expected, pathExaminer.concatenate(path, suffix).toString());
    }
}
