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

public class MethodResponderSupplierTest {

    private final GetResponder getResponderMock;
    private final InvalidMethodResponder invalidMethodResponderMock;
    private final MethodResponderSupplier methodResponderSupplier;
    private final PostResponder postResponderMock;

    public MethodResponderSupplierTest() {
        getResponderMock = mock(GetResponder.class);
        postResponderMock = mock(PostResponder.class);
        invalidMethodResponderMock = mock(InvalidMethodResponder.class);
        methodResponderSupplier = new MethodResponderSupplier(invalidMethodResponderMock);
        methodResponderSupplier.registerResponder(getResponderMock);
        methodResponderSupplier.registerResponder(postResponderMock);
    }

    @Test
    public void returnsResponderForMethod() throws Exception {
        Request request = new Request("GET", null, null, null);
        when(getResponderMock.handles(any())).thenReturn(true);

        MethodResponder methodResponder = methodResponderSupplier.supplyResponder(request);

        assertEquals(getResponderMock, methodResponder);
    }

    @Test
    public void returnsInvalidMethodIfNotPresent() throws Exception {
        Request request = new Request("AAA", null, null, null);

        MethodResponder methodResponder = methodResponderSupplier.supplyResponder(request);

        assertEquals(invalidMethodResponderMock, methodResponder);
    }

    @Test
    public void returnsAllRespondersRegistered() throws Exception {
        List<MethodResponder> actual = methodResponderSupplier.allResponders();

        assertEquals(actual.get(0), getResponderMock);
        assertEquals(actual.get(1), postResponderMock);
    }
}
