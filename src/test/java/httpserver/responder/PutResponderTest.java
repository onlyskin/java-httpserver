package httpserver.responder;

import httpserver.AppConfig;
import httpserver.header.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PutResponderTest {

    private final PutResponder putResponder;
    private final AppConfig appConfigMock;

    public PutResponderTest() {
        appConfigMock = mock(AppConfig.class);
        putResponder = new PutResponder();
    }

    @Test
    public void gets200StatusCode() throws Exception {
        Request request = new Request("PUT", "/form", new Header[0]);

        Response response = putResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns405IfNotFormUrl() throws Exception {
        Request request = new Request("POST", "/file1.txt", new Header[0]);

        Response response = putResponder.respond(appConfigMock, request);

        assertEquals(405, response.getStatusCode());
    }
}
