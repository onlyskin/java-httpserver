package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoffeeRouteTest {

    private final CoffeeRoute coffeeResponder;

    public CoffeeRouteTest() {
        coffeeResponder = new CoffeeRoute();
    }

    @Test
    public void returnsFourEighteen() throws Exception {
        Response response = coffeeResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(418, response.getStatusCode());
    }

    @Test
    public void allowsCoffee() throws Exception {
        assertTrue(coffeeResponder.allows(new Request(null, "/coffee", null, null)));
        assertFalse(coffeeResponder.allows(new Request(null, "/other", null, null)));
    }
}
