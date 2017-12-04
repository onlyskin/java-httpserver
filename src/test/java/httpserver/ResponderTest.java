package httpserver;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
import static org.junit.Assert.*;

public class ResponderTest {
    private final String tempdir;
    private final Responder responder;
    private final Path tempDirPath;
    private final Path subTempdir;
    private final Path tempFile1;
    private final Path tempFile2;

    public ResponderTest() throws IOException {
        this.tempDirPath = createTempDirectory("temp-dir");
        tempDirPath.toFile().deleteOnExit();
        this.tempdir = tempDirPath.toString() + "/";
        responder = new Responder(tempdir);

        this.subTempdir = createTempDirectory(tempDirPath, "aaaaaa");
        subTempdir.toFile().deleteOnExit();
        this.tempFile1 = createTempFile(tempDirPath, "bbbbbb", "-testfile");
        tempFile1.toFile().deleteOnExit();
        this.tempFile2 = createTempFile(tempDirPath, "cccccc", "-testfile");
        tempFile2.toFile().deleteOnExit();
    }

    @Test
    public void makesResponseForFile() throws Exception {
        String fullPath = tempFile1.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        fileOutputStream.write("Test file contents.".getBytes());
        Request request = new Request("GET", relativePath(tempFile1), new HashMap<>());

        Response response = responder.makeResponse(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents.", new String(response.getPayload()));
    }

    @Test
    public void returns404ForFileThatDoesntExist() throws Exception {
        Request request = new Request("GET", "/nonexistent_path_123456789", new HashMap<>());

        Response response = responder.makeResponse(request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }

    @Test
    public void listsDirectoryContentsForDir() throws Exception {
        Request request = new Request("GET", "", new HashMap<>());

        Response response = responder.makeResponse(request);

        assertEquals(200, response.getStatusCode());
        String expected = htmlLinkForPath(subTempdir) + "\r\n" +
                htmlLinkForPath(tempFile1) + "\r\n" +
                htmlLinkForPath(tempFile2) + "\r\n";
        assertEquals(expected, new String(response.getPayload()));
    }

    private String relativePath(Path file) {
        return file.toString().substring(tempdir.length());
    }
    
    private String htmlLinkForPath(Path file) {
        String relativePath;
        if (Files.isDirectory(file)) {
            relativePath = relativePath(file) + "/";
        } else {
            relativePath = relativePath(file);
        }
        return "<div><a href=\"/" + relativePath + "\">" + relativePath + "</a></div>";
    }
}
