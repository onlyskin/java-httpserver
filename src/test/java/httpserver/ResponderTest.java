package httpserver;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponderTest {
    private final String tempdir;

    public ResponderTest() {
        this.tempdir = System.getProperty("java.io.tmpdir");
    }

    @Test
    public void makesResponseForFile() throws Exception {
        File tempFile = File.createTempFile("temp-", "-testfile");
        tempFile.deleteOnExit();
        String fullPath = tempFile.toString();
        String relativePath = fullPath.substring(tempdir.length());

        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        fileOutputStream.write("Test file contents.".getBytes());
        Request request = new Request("GET", relativePath, new HashMap<>());
        Responder responder = new Responder(tempdir);

        Response response = responder.makeResponse(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents.", new String(response.getPayload()));
    }

    @Test
    public void returns404ForFileThatDoesntExist() throws Exception {
        Request request = new Request("GET", "/nonexistent_path_123456789", new HashMap<>());
        Responder responder = new Responder(tempdir);

        Response response = responder.makeResponse(request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }
}
