package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void returnsHelloWorld() throws Exception {
        App app = new App();
        assertEquals(app.message(), "Hello World");
    }
}
