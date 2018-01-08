package httpserver;

import httpserver.request.Request;
import httpserver.responder.GetResponder;
import httpserver.responder.InvalidMethodResponder;
import httpserver.responder.MethodResponder;
import httpserver.responder.PostResponder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ResponderSupplierTest {

    private final GetResponder getResponderMock;
    private final InvalidMethodResponder invalidMethodResponderMock;
    private final ResponderSupplier responderSupplier;
    private final PostResponder postResponderMock;

    public ResponderSupplierTest() {
        getResponderMock = mock(GetResponder.class);
        postResponderMock = mock(PostResponder.class);
        invalidMethodResponderMock = mock(InvalidMethodResponder.class);
        responderSupplier = new ResponderSupplier(invalidMethodResponderMock);
        responderSupplier.registerResponder(getResponderMock);
        responderSupplier.registerResponder(postResponderMock);
    }

    @Test
    public void returnsResponderForMethod() throws Exception {
        Request request = new Request("GET", null, null, null);
        when(getResponderMock.handles(any())).thenReturn(true);

        MethodResponder methodResponder = responderSupplier.supplyResponder(request);

        assertEquals(getResponderMock, methodResponder);
    }

    @Test
    public void returnsInvalidMethodIfNotPresent() throws Exception {
        Request request = new Request("AAA", null, null, null);

        MethodResponder methodResponder = responderSupplier.supplyResponder(request);

        assertEquals(invalidMethodResponderMock, methodResponder);
    }

    @Test
    public void returnsAllRespondersRegistered() throws Exception {
        List<MethodResponder> actual = responderSupplier.allResponders();

        assertEquals(actual.get(0), getResponderMock);
        assertEquals(actual.get(1), postResponderMock);
    }
}
