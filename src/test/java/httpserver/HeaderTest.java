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

    @Test
    public void checksEqualityByKeyAndValue() throws Exception {
        Header header1 = new Header("key", "value");
        Header header2 = new Header("key", "value");
        Header header3 = new Header("key", "val");

        assertEquals(header1, header2);
        assertNotEquals(header1, header3);
    }
}
