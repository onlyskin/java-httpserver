package httpserver.responder;

import httpserver.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.*;

public class PostResponderTest {

    private final PostResponder postResponder;
    private final Path root;

    public PostResponderTest() {
        postResponder = new PostResponder();
        root = Paths.get("test");
    }

    @Test
    public void gets200StatusCode() throws Exception {
        Request request = new Request("POST", "/form", new Header[0]);

        Response response = postResponder.respond(root, request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns405IfNotFormUrl() throws Exception {
        Request request = new Request("POST", "/file1.txt", new Header[0]);

        Response response = postResponder.respond(root, request);

        assertEquals(405, response.getStatusCode());
    }
}
