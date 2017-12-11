package httpserver.response;

import httpserver.Header;

public class FourEighteenResponse implements Response {
    @Override
    public int getStatusCode() {
        return 418;
    }

    @Override
    public byte[] getPayload() {
        return "I'm a teapot".getBytes();
    }

    @Override
    public Header[] getHeaders() {
        return new Header[0];
    }
}
