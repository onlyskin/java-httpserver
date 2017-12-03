package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodTest {
    @Test
    public void valuesAreGetPost() throws Exception {
        assertNotNull(Method.GET);
        assertNotNull(Method.HEAD);
        assertNotNull(Method.POST);
        assertNotNull(Method.PUT);
        assertNotNull(Method.DELETE);
        assertNotNull(Method.OPTIONS);
    }
}
