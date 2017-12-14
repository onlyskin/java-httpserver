package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeneralResponderTest {

    private final AppConfig appConfigMock;
    private final Request requestMock;

    public GeneralResponderTest() {
        appConfigMock = mock(AppConfig.class);
        requestMock = mock(Request.class);
        when(requestMock.getMethodString()).thenReturn("methodString");
    }

    @Test
    public void callsResponderForMethodStringOnResponderSupplier() throws Exception {
        ResponderSupplier responderSupplierMock = mock(ResponderSupplier.class);
        Responder responderMock = mock(Responder.class);
        when(responderSupplierMock.responderForMethodString(any())).thenReturn(responderMock);

        GeneralResponder generalResponder = new GeneralResponder(responderSupplierMock);

        generalResponder.respond(appConfigMock, requestMock);

        verify(responderSupplierMock).responderForMethodString("methodString");
        verify(responderMock).respond(appConfigMock, requestMock);
    }

    @Test
    public void returnsServerErrorResponseIfTheResponderCalledReturnsAnError() throws Exception {
        ResponderSupplier responderSupplierMock = mock(ResponderSupplier.class);
        Responder responderMock = mock(Responder.class);
        when(responderMock.respond(any(), any())).thenThrow(new IOException());
        when(responderSupplierMock.responderForMethodString(any())).thenReturn(responderMock);

        GeneralResponder generalResponder = new GeneralResponder(responderSupplierMock);

        Response response = generalResponder.respond(appConfigMock, requestMock);

        assertEquals(500, response.getStatusCode());
    }
}
