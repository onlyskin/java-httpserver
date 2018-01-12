package httpserver.request;

import java.io.UnsupportedEncodingException;

public class UrlDecoder {
    public String decode(String urlString) {
        try {
            return java.net.URLDecoder.decode(urlString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
