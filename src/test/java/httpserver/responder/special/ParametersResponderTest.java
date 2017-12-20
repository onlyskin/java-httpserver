package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParametersResponderTest {

    private final ParametersResponder parametersResponder;

    public ParametersResponderTest() {
        parametersResponder = new ParametersResponder();
    }

    @Test
    public void itEchoesRequestQueryStringInBody() throws Exception {
        Request requestMock = mock(Request.class);
        Parameter[] params = {new Parameter("key1", "value1"),
                new Parameter("key2", "value2")};
        when(requestMock.getParams()).thenReturn(params);

        Response response = parametersResponder.respond(mock(AppConfig.class),
                requestMock);

        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("key1 = value1"));
        assertTrue(new String(response.getPayload()).contains("key2 = value2"));
    }

    @Test
    public void handlesParameters() throws Exception {
        assertTrue(parametersResponder.handles("/parameters"));
        assertFalse(parametersResponder.handles("/other"));
    }
}
