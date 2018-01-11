package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TeaRouteTest {

    private final TeaRoute teaResponder;

    public TeaRouteTest() {
        teaResponder = new TeaRoute();
    }

    @Test
    public void returns200() throws Exception {
        Response response = teaResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void allowsTea() throws Exception {
        assertTrue(teaResponder.allows(new Request(null, "/tea", null, null)));
        assertFalse(teaResponder.allows(new Request(null, "/other", null, null)));
    }
}
