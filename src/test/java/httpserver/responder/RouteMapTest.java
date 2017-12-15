package httpserver.responder;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RouteMapTest {

    private final Responder responderMock;
    private final RouteMap routeMap;
    private final Responder[] routes;

    public RouteMapTest() {
        responderMock = mock(Responder.class);
        when(responderMock.allowed("GET")).thenReturn(true);
        when(responderMock.allowed("AAA")).thenReturn(false);
        routes = new Responder[]{responderMock};
        routeMap = new RouteMap(routes);
    }

    @Test
    public void containsRoute() throws Exception {
        assertTrue(routeMap.hasRoute("GET"));
        assertFalse(routeMap.hasRoute("AAA"));
    }

    @Test
    public void returnsResponderForRoute() throws Exception {
        assertEquals(responderMock, routeMap.getResponderForRoute("GET"));
    }
}
