package httpserver;

import httpserver.responder.GetResponder;
import httpserver.responder.InvalidMethodResponder;
import httpserver.responder.Responder;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponderSupplierTest {

    private final GetResponder getResponderMock;
    private final InvalidMethodResponder invalidMethodResponderMock;
    private final ResponderSupplier responderSupplier;

    public ResponderSupplierTest() {
        getResponderMock = mock(GetResponder.class);
        invalidMethodResponderMock = mock(InvalidMethodResponder.class);
        responderSupplier = new ResponderSupplier(invalidMethodResponderMock);
        responderSupplier.registerResponder(Method.GET, getResponderMock);
    }

    @Test
    public void returnsResponderForMethod() throws Exception {
        Responder result = responderSupplier.responderForMethodString("GET");

        assertEquals(getResponderMock, result);
    }

    @Test
    public void returnsInvalidMethodIfNotPresent() throws Exception {
        Responder result = responderSupplier.responderForMethodString("AAA");

        assertEquals(invalidMethodResponderMock, result);
    }
}
