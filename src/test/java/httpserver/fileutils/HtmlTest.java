package httpserver.fileutils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.fileutils.FileHelpers.tempDir;
import static httpserver.fileutils.FileHelpers.tempDirOptions;
import static httpserver.fileutils.FileHelpers.tempFileOptions;
import static org.junit.Assert.*;

public class HtmlTest {
    @Test
    public void makesHrefStringFromRootPathAndFullFilePath() throws Exception {
        Path root = Paths.get("/Users", "example", "public");
        Path fullFilePath = Paths.get("/Users", "example", "public", "example.txt");

        String expected = "example.txt";
        String actual = Html.hrefString(root, fullFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void makesHrefStringFromRootPathAndDirPath() throws Exception {
        Path root = tempDir();
        Path fullDirPath = tempDirOptions(root);

        String expected = fullDirPath.toString()
                .substring(root.toString().length() + 1) + "/";
        String actual = Html.hrefString(root, fullDirPath);
        assertEquals(expected, actual);
    }

    @Test
    public void makesHtmlLinkFromRootPathAndDirPath() throws Exception {
        Path root = tempDir();
        Path fullFilePath = tempFileOptions(root, "aaa");
        Path fullDirPath = tempDirOptions(root);

        String expectedFilePath = fullFilePath.toString()
                .substring(root.toString().length() + 1);
        String expectedDirPath = fullDirPath.toString()
                .substring(root.toString().length() + 1) + "/";

        String expectedFileLink = "<div><a href=\"" + expectedFilePath +
                "\">" + expectedFilePath + "</a></div>";
        String expectedDirLink = "<div><a href=\"" + expectedDirPath +
                "\">" + expectedDirPath + "</a></div>";

        String actualFileLink = Html.linkString(root, fullFilePath);
        String actualDirLink = Html.linkString(root, fullDirPath);

        assertEquals(expectedFileLink, actualFileLink);
        assertEquals(expectedDirLink, actualDirLink);
    }
}
