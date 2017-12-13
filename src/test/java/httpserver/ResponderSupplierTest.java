package httpserver;

import httpserver.responder.GetResponder;
import httpserver.responder.InvalidMethodResponder;
import httpserver.responder.Responder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponderSupplierTest {

    private final Map<Method, Responder> methodResponderMap;
    private final GetResponder getResponderMock;
    private final InvalidMethodResponder invalidMethodResponderMock;
    private final ResponderSupplier responderSupplier;

    public ResponderSupplierTest() {
        methodResponderMap = new HashMap<>();
        getResponderMock = mock(GetResponder.class);
        invalidMethodResponderMock = mock(InvalidMethodResponder.class);
        methodResponderMap.put(Method.GET, getResponderMock);
        responderSupplier = new ResponderSupplier(methodResponderMap, invalidMethodResponderMock);
    }

    @Test
    public void returnsResponderForMethod() {
        Responder result = responderSupplier.responderForMethodString("GET");

        assertEquals(getResponderMock, result);
    }

    @Test
    public void returnsInvalidMethodIfNotPresent() {
        Responder result = responderSupplier.responderForMethodString("AAA");

        assertEquals(invalidMethodResponderMock, result);
    }
}
