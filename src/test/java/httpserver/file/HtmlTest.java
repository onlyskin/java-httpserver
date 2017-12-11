package httpserver.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempDirOptions;
import static httpserver.file.FileHelpers.tempFileOptions;
import static org.junit.Assert.*;

public class HtmlTest {

    private final Path root;
    private final Path fullDirPath;
    private final Path fullFilePath;

    public HtmlTest() throws IOException {
        root = tempDir();
        fullDirPath = tempDirOptions(root);
        fullFilePath = tempFileOptions(root, "aaa", "temp");
    }

    @Test
    public void makesHrefStringFromRootPathAndFullFilePath() throws Exception {
        Path root = Paths.get("/Users", "example", "public");
        Path fullFilePath = Paths.get("/Users", "example", "public", "example.txt");

        assertEquals("example.txt", Html.hrefString(root, fullFilePath));
    }

    @Test
    public void makesHrefStringFromRootPathAndDirPath() throws Exception {
        String expected = fullDirPath.toString().substring(root.toString().length() + 1) + "/";

        assertEquals(expected, Html.hrefString(root, fullDirPath));
    }

    @Test
    public void makesHtmlLinkFromRootPathAndDirPath() throws Exception {
        String expectedFilePath = fullFilePath.toString()
                .substring(root.toString().length() + 1);
        String expectedDirPath = fullDirPath.toString()
                .substring(root.toString().length() + 1) + "/";
        String expectedFileLink = "<div><a href=\"/" + expectedFilePath +
                "\">/" + expectedFilePath + "</a></div>";
        String expectedDirLink = "<div><a href=\"/" + expectedDirPath +
                "\">/" + expectedDirPath + "</a></div>";

        assertEquals(expectedFileLink, Html.linkString(root, fullFilePath));
        assertEquals(expectedDirLink, Html.linkString(root, fullDirPath));
    }
}
