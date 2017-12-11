package httpserver.responder;

import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutResponderTest {

    private final Path root;
    private final PutResponder putResponder;

    public PutResponderTest() {
        root = Paths.get("test");
        putResponder = new PutResponder();
    }

    @Test
    public void gets200StatusCode() throws Exception {
        Request request = new Request("PUT", "/form", new HashMap<>());

        Response response = putResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns405IfNotFormUrl() throws Exception {
        Request request = new Request("POST", "/file1.txt", new HashMap<>());

        Response response = putResponder.respond(root, request);

        assertEquals(405, response.getStatusCode());
    }
}
