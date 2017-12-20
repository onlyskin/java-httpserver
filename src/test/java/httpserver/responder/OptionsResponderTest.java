package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.ResponderSupplier;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OptionsResponderTest {
    @Test
    public void responderHasListOfAllowedMethodsInAllowHeader() throws Exception {
        Responder trueResponderMock = mock(Responder.class);
        when(trueResponderMock.handles(any())).thenReturn(true);
        Responder falseResponderMock = mock(Responder.class);
        when(falseResponderMock.handles(any())).thenReturn(false);

        ResponderSupplier responderSupplier = new ResponderSupplier(mock(InvalidMethodResponder.class));
        responderSupplier.registerResponder(Method.GET, trueResponderMock);
        responderSupplier.registerResponder(Method.HEAD, trueResponderMock);
        responderSupplier.registerResponder(Method.POST, falseResponderMock);
        responderSupplier.registerResponder(Method.PUT, trueResponderMock);
        responderSupplier.registerResponder(Method.PATCH, falseResponderMock);
        responderSupplier.registerResponder(Method.DELETE, falseResponderMock);
        responderSupplier.registerResponder(Method.OPTIONS, trueResponderMock);

        Request request = new Request("OPTIONS", "", new Header[0], "");

        OptionsResponder optionsResponder = new OptionsResponder(responderSupplier);
        Response response = optionsResponder.respond(mock(AppConfig.class), request);

        assertEquals(200, response.getStatusCode());
        assertEquals(new Header("Allow", "GET,HEAD,PUT,OPTIONS"), response.getHeaders()[0]);
    }

    @Test
    public void handlesForm() throws Exception {
        OptionsResponder optionsResponder = new OptionsResponder(null);
        assertTrue(optionsResponder.handles("/form"));
        assertTrue(optionsResponder.handles("anything"));
    }
}
