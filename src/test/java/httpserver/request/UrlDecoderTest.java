package httpserver.request;

import httpserver.request.UrlDecoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class UrlDecoderTest {
    @Test
    public void decodesUrl() throws Exception{
        String input = "key1=value1%3C%2C%3F";
        UrlDecoder urlDecoder = new UrlDecoder();
        assertEquals("key1=value1<,?", urlDecoder.decode(input));
    }
}
