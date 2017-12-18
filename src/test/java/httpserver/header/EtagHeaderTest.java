package httpserver.header;

import org.junit.Test;

import static org.junit.Assert.*;

public class EtagHeaderTest {
    @Test
    public void hasCorrectValue() throws Exception {
        Header expected = new Header("ETag", "bfc13a");

        assertEquals(expected, new EtagHeader("bfc13a"));
    }
}
