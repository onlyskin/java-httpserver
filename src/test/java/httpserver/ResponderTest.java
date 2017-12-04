package httpserver;

import org.junit.Test;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.HashMap;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
import static org.junit.Assert.*;

public class ResponderTest {
    private final String tempdir;
    private final Responder responder;

    public ResponderTest() {
        this.tempdir = System.getProperty("java.io.tmpdir");
        responder = new Responder(tempdir);
    }

    @Test
    public void makesResponseForFile() throws Exception {
        Path tempFile = createTempFile("temp-", "-testfile");
        tempFile.toFile().deleteOnExit();
        String fullPath = tempFile.toString();

        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        fileOutputStream.write("Test file contents.".getBytes());
        Request request = new Request("GET", relativePath(tempFile), new HashMap<>());

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
        Path subTempdir = createTempDirectory("temp-dir");
        subTempdir.toFile().deleteOnExit();
        Path tempFile1 = createTempFile("temp-", "-testfile");
        tempFile1.toFile().deleteOnExit();
        Path tempFile2 = createTempFile("temp-", "-testfile");
        tempFile2.toFile().deleteOnExit();

        Request request = new Request("GET", "/", new HashMap<>());
        Response response = responder.makeResponse(request);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains(htmlLinkForPath(subTempdir)));
        assertTrue(new String(response.getPayload()).contains(htmlLinkForPath(tempFile1)));
        assertTrue(new String(response.getPayload()).contains(htmlLinkForPath(tempFile2)));
    }

    private String relativePath(Path file) {
        return file.toString().substring(tempdir.length());
    }
    
    private String htmlLinkForPath(Path file) {
        return "<a href=\"" + relativePath(file) + "\">" + relativePath(file) + "</a>";
    }
}
