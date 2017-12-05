package httpserver.responder;

import httpserver.Request;
import httpserver.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static junit.framework.TestCase.*;

public class PostResponderTest {
    @Test
    public void gets200StatusCode() throws Exception {
        Path root = Paths.get("test");
        Request request = new Request("POST", "/form", new HashMap<>());
        PostResponder postResponder = new PostResponder();

        Response response = postResponder.respond(root, request);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns404IfNotFormUrl() throws Exception {
        Path root = Paths.get("test");
        Request request = new Request("POST", "/file1.txt", new HashMap<>());
        PostResponder postResponder = new PostResponder();

        Response response = postResponder.respond(root, request);
        assertEquals(404, response.getStatusCode());
    }
}
