package httpserver.responder;

import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class GetResponderTest {

    private final GetResponder getResponder;
    private final Path root;
    private final Path file1;
    private final Path file2;
    private final Path fileWithContents;

    public GetResponderTest() throws IOException {
        getResponder = new GetResponder();
        root = tempDir();
        file1 = tempFileOptions(root, "aaa");
        file2 = tempFileOptions(root, "bbb");
        fileWithContents = tempFileOptions(root, "aaa");
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
    public void returnsFileContentsWhenFile() throws Exception {
        String fileName = fileWithContents.toString().substring(root.toString().length());
        Request request = new Request("GET", fileName,
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents", new String(response.getPayload()));
    }

    @Test
    public void returnsLinksWhenDir() throws Exception {
        Request request = new Request("GET", "/",
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("<a href="));
    }
}
