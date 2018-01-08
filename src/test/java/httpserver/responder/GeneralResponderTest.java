package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.MethodResponderSupplier;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeneralResponderTest {

    private final AppConfig appConfigMock;
    private final Request requestMock;
    private final MethodResponderSupplier methodResponderSupplierMock;
    private final MethodResponder methodResponderMock;
    private final GeneralResponder generalResponder;

    public GeneralResponderTest() {
        appConfigMock = mock(AppConfig.class);
        requestMock = mock(Request.class);
        methodResponderSupplierMock = mock(MethodResponderSupplier.class);
        methodResponderMock = mock(MethodResponder.class);
        generalResponder = new GeneralResponder(methodResponderSupplierMock);
    }

    @Test
    public void callsSupplyResponderOnResponderSupplierWithRequest() throws Exception {
        when(methodResponderSupplierMock.supplyResponder(any())).thenReturn(methodResponderMock);

        generalResponder.respond(appConfigMock, requestMock);

        verify(methodResponderSupplierMock).supplyResponder(requestMock);
        verify(methodResponderMock).respond(appConfigMock, requestMock);
    }

    @Test
    public void returnsServerErrorResponseIfTheResponderCalledReturnsAnError() throws Exception {
        when(methodResponderMock.respond(any(), any())).thenThrow(new IOException());
        when(methodResponderSupplierMock.supplyResponder(any())).thenReturn(methodResponderMock);

        Response response = generalResponder.respond(appConfigMock, requestMock);

        assertEquals(500, response.getStatusCode());
    }
}
