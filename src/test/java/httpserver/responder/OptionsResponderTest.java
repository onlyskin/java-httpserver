package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OptionsResponderTest {
    @Test
    public void responderHasListOfAllowedMethodsInAllowHeader() throws Exception {
        Map<Method, Responder> methodResponderMapMock = new HashMap<>();
        Responder trueResponderMock = mock(Responder.class);
        when(trueResponderMock.allowed(any())).thenReturn(true);
        Responder falseResponderMock = mock(Responder.class);
        when(falseResponderMock.allowed(any())).thenReturn(false);

        methodResponderMapMock.put(Method.GET, trueResponderMock);
        methodResponderMapMock.put(Method.POST, falseResponderMock);
        methodResponderMapMock.put(Method.PUT, trueResponderMock);
        methodResponderMapMock.put(Method.DELETE, falseResponderMock);
        methodResponderMapMock.put(Method.OPTIONS, trueResponderMock);
        ResponderSupplier responderSupplier = new ResponderSupplier(methodResponderMapMock,
                mock(InvalidMethodResponder.class));

        Request request = new Request("OPTIONS", "", new Header[0], "");

        OptionsResponder optionsResponder = new OptionsResponder(responderSupplier);
        Response response = optionsResponder.respond(mock(AppConfig.class), request);

        assertEquals(200, response.getStatusCode());
        assertEquals(new Header("Allow", "GET, PUT, OPTIONS"), response.getHeaders()[0]);
    }

    @Test
    public void formIsAllowed() throws Exception {
        OptionsResponder optionsResponder = new OptionsResponder(null);
        assertTrue(optionsResponder.allowed("/form"));
        assertTrue(optionsResponder.allowed("anything"));
    }
}
