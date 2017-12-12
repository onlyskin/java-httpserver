package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParametersResponderTest {
    @Test
    public void itEchoesRequestQueryStringInBody() {
        Request requestMock = mock(Request.class);
        Parameter[] params = {new Parameter("key1", "value1"),
                new Parameter("key2", "value2")};
        when(requestMock.getParams()).thenReturn(params);
        ParametersResponder parametersResponder = new ParametersResponder();

        Response response = parametersResponder.respond(mock(AppConfig.class),
                requestMock);
        assertEquals(200, response.getStatusCode());
        assertTrue(new String(response.getPayload()).contains("key1 = value1"));
        assertTrue(new String(response.getPayload()).contains("key2 = value2"));
    }
}
