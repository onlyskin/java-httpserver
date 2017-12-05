package httpserver.fileutils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.fileutils.FileHelpers.tempDir;
import static httpserver.fileutils.FileHelpers.tempDirOptions;
import static org.junit.Assert.*;

public class UriTest {
    @Test
    public void makesHrefStringFromRootPathAndFullFilePath() throws Exception {
        Path root = Paths.get("/Users", "example", "public");
        Path fullFilePath = Paths.get("/Users", "example", "public", "example.txt");

        String expected = "example.txt";
        String actual = Uri.hrefString(root, fullFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void makesHrefStringFromRootPathAndDirPath() throws Exception {
        Path root = tempDir();
        Path fullDirPath = tempDirOptions(root);

        String rootString = root.toString();
        String fullDirString = fullDirPath.toString();
        String expected = fullDirString.substring(rootString.length() + 1) + "/";
        String actual = Uri.hrefString(root, fullDirPath);
        assertEquals(expected, actual);
    }
}
