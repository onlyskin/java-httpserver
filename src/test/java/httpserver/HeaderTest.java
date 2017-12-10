package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeaderTest {
    @Test
    public void hasKey() throws Exception {
        Header header = new Header("key", "value");

        assertEquals("key", header.getKey());
    }

    @Test
    public void hasValue() throws Exception {
        Header header = new Header("key", "value");

        assertEquals("value", header.getValue());
    }
}
