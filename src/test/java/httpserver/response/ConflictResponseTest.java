package httpserver.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConflictResponseTest {
    @Test
    public void has409StatusCode() throws Exception {
        ConflictResponse conflictResponse = new ConflictResponse();

        assertEquals(409, conflictResponse.getStatusCode());
    }
}
