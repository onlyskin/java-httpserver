package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterTest {
    @Test
    public void hasKey() throws Exception {
        Parameter parameter = new Parameter("key", "value");

        assertEquals("key", parameter.getKey());
    }

    @Test
    public void hasValue() throws Exception {
        Parameter parameter = new Parameter("key", "value");

        assertEquals("value", parameter.getValue());
    }

    @Test
    public void checksEqualityByKeyAndValue() throws Exception {
        Parameter parameter1 = new Parameter("key", "value");
        Parameter parameter2 = new Parameter("key", "value");
        Parameter parameter3 = new Parameter("key", "val");

        assertEquals(parameter1, parameter2);
        assertNotEquals(parameter1, parameter3);
    }
}
