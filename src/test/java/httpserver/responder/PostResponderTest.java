package httpserver.responder;

import httpserver.AppConfig;
import httpserver.header.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class PostResponderTest {

    private final PostResponder postResponder;
    private final Path root;
    private final AppConfig appConfig;

    public PostResponderTest() {
        postResponder = new PostResponder();
        root = Paths.get("test");
        appConfig = mock(AppConfig.class);
    }

    @Test
    public void gets200StatusCode() throws Exception {
        Request request = new Request("POST", "/form", new Header[0], "");

        Response response = postResponder.respond(appConfig, request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns405IfNotFormUrl() throws Exception {
        Request request = new Request("POST", "/file1.txt", new Header[0], "");

        Response response = postResponder.respond(appConfig, request);

        assertEquals(405, response.getStatusCode());
    }
}
