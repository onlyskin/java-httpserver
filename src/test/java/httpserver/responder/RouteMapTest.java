package httpserver.responder;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RouteMapTest {

    private final Map<String, Responder> routes;
    private final Responder responderMock;
    private final RouteMap routeMap;

    public RouteMapTest() {
        responderMock = mock(Responder.class);
        routes = new HashMap<>();
        routes.put("GET", responderMock);
        routeMap = new RouteMap(routes);
    }

    @Test
    public void containsRouteReturnsTrue() throws Exception {
        assertTrue(routeMap.contains("GET"));
        assertFalse(routeMap.contains("AAA"));
    }

    @Test
    public void returnsResponderForRoute() throws Exception {
        assertEquals(responderMock, routeMap.getResponder("GET"));
    }
}
