package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoffeeResponderTest {
    @Test
    public void returnsFourEighteen() throws Exception {
        CoffeeResponder coffeeResponder = new CoffeeResponder();

        Response response = coffeeResponder.respond(mock(AppConfig.class),
                mock(Request.class));
        assertEquals(418, response.getStatusCode());
    }
}
