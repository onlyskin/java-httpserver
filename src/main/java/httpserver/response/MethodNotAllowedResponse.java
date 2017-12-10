package httpserver.response;

import httpserver.Header;

public class MethodNotAllowedResponse implements Response {
    @Override
    public int getStatusCode() {
        return 405;
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public Header[] getHeaders() {
        return new Header[0];
    }
}
