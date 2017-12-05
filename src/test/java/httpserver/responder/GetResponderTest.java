package httpserver.responder;

import httpserver.Request;
import httpserver.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static httpserver.fileutils.FileHelpers.tempDir;
import static httpserver.fileutils.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class GetResponderTest {

    private final GetResponder getResponder;

    public GetResponderTest() {
        getResponder = new GetResponder();
    }

    @Test
    public void returns404ForBadPath() throws Exception {
        Path root = Paths.get("456");
        Request request = new Request("GET",
                "nonexistentfile123",
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }

    @Test
    public void returnsFileContentsWhenFile() throws Exception {
        Path root = tempDir();
        Path file = tempFileOptions(root, "aaa");
        write(file, "Test file contents".getBytes());
        String fileName = file.toString().substring(root.toString().length());
        Request request = new Request("GET", fileName,
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents", new String(response.getPayload()));
    }

    @Test
    public void returnsLinksWhenDir() throws Exception {
        Path root = tempDir();
        Path file1 = tempFileOptions(root, "aaa");
        Path file2 = tempFileOptions(root, "bbb");
        String fileName1 = file1.toString().substring(root.toString().length());
        String fileName2 = file1.toString().substring(root.toString().length());
        Request request = new Request("GET", "/",
                new HashMap<>());

        Response response = getResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("<a href="));
    }
}
