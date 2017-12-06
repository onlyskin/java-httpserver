package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodTest {
    @Test
    public void valuesAreGetPost() throws Exception {
        assertNotNull(Method.GET);
        assertNotNull(Method.POST);
        assertNotNull(Method.PUT);
    }
}
