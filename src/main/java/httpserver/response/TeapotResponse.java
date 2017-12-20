package httpserver.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TeapotResponse extends Response {
    public TeapotResponse() {
        InputStream inputStream = new ByteArrayInputStream("I'm a teapot".getBytes());
        super.setPayloadStream(inputStream);
    }

    public int getStatusCode() {
        return 418;
    }
}
