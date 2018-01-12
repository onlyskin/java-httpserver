package httpserver;

import httpserver.request.Request;
import httpserver.responder.GetResponder;
import httpserver.responder.MethodResponder;
import httpserver.responder.PostResponder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MethodRouteSupplierTest {

    private final GetResponder getResponderMock;
    private final MethodResponderSupplier methodResponderSupplier;
    private final PostResponder postResponderMock;

    public MethodRouteSupplierTest() {
        getResponderMock = mock(GetResponder.class);
        postResponderMock = mock(PostResponder.class);
        methodResponderSupplier = new MethodResponderSupplier();
        methodResponderSupplier.registerResponder(getResponderMock);
        methodResponderSupplier.registerResponder(postResponderMock);
    }

    @Test
    public void returnsResponderForMethod() throws Exception {
        Request request = new Request(Method.GET, null, null, null);
        when(getResponderMock.handles(request)).thenReturn(true);

        MethodResponder methodResponder = methodResponderSupplier.supplyResponder(request);

        assertEquals(getResponderMock, methodResponder);
    }

    @Test(expected = Exception.class)
    public void throwsErrorWhenNoResponderForRequestMethod() throws Exception {
        Request request = new Request(Method.PUT, null, null, null);
        when(getResponderMock.handles(request)).thenReturn(false);
        when(postResponderMock.handles(request)).thenReturn(false);

        methodResponderSupplier.supplyResponder(request);
    }

    @Test
    public void returnsAllRespondersRegistered() throws Exception {
        List<MethodResponder> actual = methodResponderSupplier.allResponders();

        assertEquals(actual.get(0), getResponderMock);
        assertEquals(actual.get(1), postResponderMock);
    }
}
