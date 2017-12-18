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

    @Test
    public void matchesBytesToHash() throws Exception {
        Hasher hasher = new Hasher();
        byte[] input = "default content".getBytes();
        String hash = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
        String bad_hash = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";

        assertTrue(hasher.matches(input, hash));
        assertFalse(hasher.matches(input, bad_hash));
    }
}
