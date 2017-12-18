package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class HasherTest {
    @Test
    public void returnsHashForBytes() throws Exception {
        Hasher hasher = new Hasher();
        byte[] input = "default content".getBytes();
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec",
                hasher.getHash(input));
    }
}
