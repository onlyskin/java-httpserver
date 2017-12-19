package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.responder.special.CoffeeResponder;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoffeeResponderTest {

    private final CoffeeResponder coffeeResponder;

    public CoffeeResponderTest() {
        coffeeResponder = new CoffeeResponder();
    }

    @Test
    public void returnsFourEighteen() throws Exception {
        Response response = coffeeResponder.respond(mock(AppConfig.class),
                mock(Request.class));

        assertEquals(418, response.getStatusCode());
    }

    @Test
    public void coffeeIsAllowed() throws Exception {
        assertTrue(coffeeResponder.allowed("/coffee"));
        assertFalse(coffeeResponder.allowed("/other"));
    }
}
