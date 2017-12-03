package httpserver;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponderTest {
    @Test
    public void makesResponseForFile() throws Exception {
        File tempFile = File.createTempFile("temp-", "-testfile");
        tempFile.deleteOnExit();
        String tempFilePath = tempFile.toString();
        FileOutputStream fileOutputStream = new FileOutputStream(tempFilePath);
        fileOutputStream.write("Test file contents.".getBytes());
        Request request = new Request("GET", tempFilePath, new HashMap<String, String>());
        Responder responder = new Responder();

        Response response = responder.makeResponse(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents.", new String(response.getPayload()));
    }

    @Test
    public void returns404ForFileThatDoesntExist() throws Exception {
        Request request = new Request("GET", "/nonexistent_path_123456789", new HashMap<String, String>());
        Responder responder = new Responder();

        Response response = responder.makeResponse(request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }
}
