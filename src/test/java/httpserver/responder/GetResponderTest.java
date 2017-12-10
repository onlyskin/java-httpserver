package httpserver.responder;

import httpserver.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class GetResponderTest {

    private final GetResponder getResponder;
    private final Path root;
    private final Path fileWithContents;

    public GetResponderTest() throws IOException {
        getResponder = new GetResponder();
        root = tempDir();
        fileWithContents = tempFileOptions(root, "aaa", ".gif");
        write(fileWithContents, "Test file contents".getBytes());
    }

    @Test
    public void returns404ForBadPath() throws Exception {
        Request request = new Request("GET",
                "nonexistentfile123",
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }

    @Test
    public void getRequestForFile() throws Exception {
        String fileName = fileWithContents.toString().substring(root.toString().length());
        Request request = new Request("GET", fileName,
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        Header[] expectedHeaders = new Header[]{new Header("Content-Type", "image/gif")};
        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents", new String(response.getPayload()));
        assertEquals(expectedHeaders, response.getHeaders());
    }

    @Test
    public void getRequestForDir() throws Exception {
        Request request = new Request("GET", "/",
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("<a href="));
    }
}
