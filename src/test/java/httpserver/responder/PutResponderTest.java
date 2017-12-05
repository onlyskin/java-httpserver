package httpserver.responder;

import httpserver.Request;
import httpserver.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutResponderTest {
    @Test
    public void gets200StatusCode() throws Exception {
        Path root = Paths.get("test");
        Request request = new Request("PUT", "/form", new HashMap<>());
        PutResponder putResponder = new PutResponder();

        Response response = putResponder.respond(root, request);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns405IfNotFormUrl() throws Exception {
        Path root = Paths.get("test");
        Request request = new Request("POST", "/file1.txt", new HashMap<>());
        PostResponder postResponder = new PostResponder();

        Response response = postResponder.respond(root, request);
        assertEquals(405, response.getStatusCode());
    }
}
