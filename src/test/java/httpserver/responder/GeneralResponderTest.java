package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.ResponderSupplier;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GeneralResponderTest {
    @Test
    public void callsResponderForMethodStringOnResponderSupplier() throws Exception {
        ResponderSupplier responderSupplierMock = mock(ResponderSupplier.class);
        Responder responderMock = mock(Responder.class);
        when(responderSupplierMock.responderForMethodString(any())).thenReturn(responderMock);

        GeneralResponder generalResponder = new GeneralResponder(responderSupplierMock);

        AppConfig appConfigMock = mock(AppConfig.class);
        Request requestMock = mock(Request.class);
        when(requestMock.getMethodString()).thenReturn("methodString");
        generalResponder.respond(appConfigMock, requestMock);

        verify(responderSupplierMock).responderForMethodString("methodString");
        verify(responderMock).respond(appConfigMock, requestMock);
    }
}
