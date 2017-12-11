package httpserver.responder;

import httpserver.App;
import httpserver.AppConfig;
import httpserver.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetResponderTest {

    private final GetResponder getResponder;
    private final Path root;
    private final Path fileWithContents;
    private final AppConfig appConfigMock;

    public GetResponderTest() throws IOException {
        getResponder = new GetResponder();
        root = tempDir();
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(root);
        fileWithContents = tempFileOptions(root, "aaa", ".gif");
        write(fileWithContents, "Test file contents".getBytes());
    }

    @Test
    public void returns404ForBadPath() throws Exception {
        Request request = new Request("GET",
                "nonexistentfile123", new Header[0]);

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }

    @Test
    public void getRequestForFile() throws Exception {
        String fileName = fileWithContents.toString().substring(root.toString().length());
        Request request = new Request("GET", fileName, new Header[0]);

        Response response = getResponder.respond(appConfigMock, request);

        Header[] expectedHeaders = new Header[]{new Header("Content-Type", "image/gif")};
        assertEquals(200, response.getStatusCode());
        assertEquals("Test file contents", new String(response.getPayload()));
        assertEquals(expectedHeaders, response.getHeaders());
    }

    @Test
    public void getRequestForDir() throws Exception {
        Request request = new Request("GET", "/", new Header[0]);

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("<a href="));
    }

    @Test
    public void getRequestToCoffee() throws Exception {
        Request request = new Request("GET", "/coffee", new Header[0]);

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(418, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("I'm a teapot"));
    }

    @Test
    public void getRequestToTea() throws Exception {
        Request request = new Request("GET", "/tea", new Header[0]);

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
    }
}
