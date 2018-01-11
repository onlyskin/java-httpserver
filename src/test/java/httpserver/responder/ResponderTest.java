package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.MethodResponderSupplier;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponderTest {

    private final AppConfig appConfigMock;
    private final Request requestMock;
    private final MethodResponderSupplier methodResponderSupplierMock;
    private final MethodResponder methodResponderMock;
    private final Responder responder;

    public ResponderTest() {
        appConfigMock = mock(AppConfig.class);
        requestMock = mock(Request.class);
        methodResponderSupplierMock = mock(MethodResponderSupplier.class);
        methodResponderMock = mock(MethodResponder.class);
        responder = new Responder(methodResponderSupplierMock);
    }

    @Test
    public void callsSupplyResponderOnResponderSupplierWithRequest() throws Exception {
        when(methodResponderMock.allows(any(Request.class))).thenReturn(true);
        when(methodResponderSupplierMock.supplyResponder(any())).thenReturn(methodResponderMock);

        responder.respond(appConfigMock, requestMock);

        verify(methodResponderSupplierMock).supplyResponder(requestMock);
        verify(methodResponderMock).respond(appConfigMock, requestMock);
    }

    @Test
    public void returns405IfTheResponderCalledDoesntAllowTheRequest() throws Exception {
        when(methodResponderSupplierMock.supplyResponder(any())).thenReturn(methodResponderMock);
        when(methodResponderMock.allows(any(Request.class))).thenReturn(false);

        Response response = responder.respond(appConfigMock, requestMock);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void returns405IfTheMethodSupplierThrowsAnException() throws Exception {
        when(methodResponderSupplierMock.supplyResponder(any())).thenThrow(new Exception());

        Response response = responder.respond(appConfigMock, requestMock);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void returnsServerErrorResponseIfTheResponderCalledReturnsAnError() throws Exception {
        when(methodResponderMock.allows(any(Request.class))).thenReturn(true);
        when(methodResponderMock.respond(any(), any())).thenThrow(new IOException());
        when(methodResponderSupplierMock.supplyResponder(any())).thenReturn(methodResponderMock);

        Response response = responder.respond(appConfigMock, requestMock);

        assertEquals(500, response.getStatusCode());
    }
}
