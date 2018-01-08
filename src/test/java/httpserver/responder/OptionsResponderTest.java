package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.ResponderSupplier;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OptionsResponderTest {
    @Test
    public void responderHasListOfAllowedMethodsInAllowHeader() throws Exception {
        MethodResponder getResponderMock = mock(GetResponder.class);
        when(getResponderMock.allows("")).thenReturn(true);
        when(getResponderMock.getMethodString()).thenReturn("GET");

        MethodResponder headResponderMock = mock(HeadResponder.class);
        when(headResponderMock.allows("")).thenReturn(true);
        when(headResponderMock.getMethodString()).thenReturn("HEAD");

        MethodResponder putResponderMock = mock(PutResponder.class);
        when(putResponderMock.allows("")).thenReturn(false);

        ResponderSupplier responderSupplier = new ResponderSupplier(mock(InvalidMethodResponder.class));
        responderSupplier.registerResponder(getResponderMock);
        responderSupplier.registerResponder(headResponderMock);
        responderSupplier.registerResponder(putResponderMock);

        Request request = new Request("OPTIONS", "", new Header[0], "");

        OptionsResponder optionsResponder = new OptionsResponder(responderSupplier);
        Response response = optionsResponder.respond(mock(AppConfig.class), request);

        assertEquals(200, response.getStatusCode());
        assertEquals(new Header("Allow", "GET,HEAD"), response.getHeaders()[0]);
    }

    @Test
    public void allowsForm() throws Exception {
        OptionsResponder optionsResponder = new OptionsResponder(null);
        assertTrue(optionsResponder.allows("/form"));
        assertTrue(optionsResponder.allows("anything"));
    }

    @Test
    public void onlyHandlesOPTIONS() throws Exception {
        OptionsResponder optionsResponder = new OptionsResponder(null);
        Request optionsRequest = new Request("OPTIONS", "", null, "");
        Request getRequest = new Request("GET", "", null, "");

        assertFalse(optionsResponder.handles(getRequest));
        assertTrue(optionsResponder.handles(optionsRequest));
    }
}
