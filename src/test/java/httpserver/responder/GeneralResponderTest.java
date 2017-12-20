package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.ResponderSupplier;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeneralResponderTest {

    private final AppConfig appConfigMock;
    private final Request requestMock;
    private final ResponderSupplier responderSupplierMock;
    private final Responder responderMock;
    private final GeneralResponder generalResponder;

    public GeneralResponderTest() {
        appConfigMock = mock(AppConfig.class);
        requestMock = mock(Request.class);
        when(requestMock.getMethodString()).thenReturn("methodString");
        responderSupplierMock = mock(ResponderSupplier.class);
        responderMock = mock(Responder.class);
        generalResponder = new GeneralResponder(responderSupplierMock);
    }

    @Test
    public void callsResponderForMethodStringOnResponderSupplier() throws Exception {
        when(responderSupplierMock.responderForMethodString(any())).thenReturn(responderMock);

        generalResponder.respond(appConfigMock, requestMock);

        verify(responderSupplierMock).responderForMethodString("methodString");
        verify(responderMock).respond(appConfigMock, requestMock);
    }

    @Test
    public void returnsServerErrorResponseIfTheResponderCalledReturnsAnError() throws Exception {
        when(responderMock.respond(any(), any())).thenThrow(new IOException());
        when(responderSupplierMock.responderForMethodString(any())).thenReturn(responderMock);

        Response response = generalResponder.respond(appConfigMock, requestMock);

        assertEquals(500, response.getStatusCode());
    }

    @Test
    public void handlesAllPaths() throws Exception {
        assertTrue(generalResponder.handles("/anything"));
        assertTrue(generalResponder.handles("/other"));
    }
}
