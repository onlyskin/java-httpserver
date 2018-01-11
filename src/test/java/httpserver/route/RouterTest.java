package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RouterTest {

    private final Route routeMock1;
    private final Route routeMock2;
    private final Route[] routes;
    private final Router router;
    private final AppConfig appConfigMock;
    private final Request requestMock;

    public RouterTest() {
        routeMock1 = mock(Route.class);
        routeMock2 = mock(Route.class);
        routes = new Route[]{routeMock1, routeMock2};
        router = new Router(routes);
        appConfigMock = mock(AppConfig.class);
        requestMock = mock(Request.class);
    }

    @Test
    public void hasRouteTrueWhenARouteAllowsPath() throws Exception {
        when(routeMock1.allows(any())).thenReturn(false);
        when(routeMock2.allows(any())).thenReturn(true);

        assertTrue(router.canRespond(null));
    }

    @Test
    public void hasRouteFalseWhenNoRouteAllowsPath() throws Exception {
        when(routeMock1.allows(any())).thenReturn(false);
        when(routeMock2.allows(any())).thenReturn(false);

        assertFalse(router.canRespond(null));
    }

    @Test
    public void returnsRouteForRequest() throws Exception {
        when(routeMock1.allows(any())).thenReturn(false);
        when(routeMock2.allows(any())).thenReturn(true);

        Route actual = router.routeForRequest(null);

        assertEquals(actual, routeMock2);
    }

    @Test
    public void respondsToRequest() throws Exception {
        when(routeMock1.allows(any())).thenReturn(false);
        when(routeMock2.allows(any())).thenReturn(true);
        Response responseMock = mock(Response.class);
        when(routeMock2.respond(any(), any())).thenReturn(responseMock);

        Response actual = router.respond(appConfigMock, requestMock);

        verify(routeMock2).respond(appConfigMock, requestMock);
        assertEquals(responseMock, actual);
    }

    @Test
    public void returns500WhenRouteRespondErrors() throws Exception {
        when(routeMock1.allows(any())).thenReturn(false);
        when(routeMock2.allows(any())).thenReturn(true);
        when(routeMock2.respond(any(), any())).thenThrow(new IOException());

        Response response = router.respond(appConfigMock, requestMock);

        assertEquals(500, response.getStatusCode());
    }
}
