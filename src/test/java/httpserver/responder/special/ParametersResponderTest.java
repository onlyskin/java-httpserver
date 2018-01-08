package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.OutputStream;

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
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("key1 = value1\r\nkey2 = value2\r\n".getBytes());
    }

    @Test
    public void allowsParameters() throws Exception {
        assertTrue(parametersResponder.allows("/parameters"));
        assertFalse(parametersResponder.allows("/other"));
    }
}
