package httpserver.response;

import httpserver.Header;

public class OkResponse implements Response {
    private final byte[] payload;
    private final Header[] headers;

    public OkResponse(byte[] payload) {
        this.payload = payload;
        this.headers = new Header[0];
    }

    public OkResponse(byte[] payload, Header[] headers) {
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public int getStatusCode() {
        return 200;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }

    public Header[] getHeaders() {
        return this.headers;
    }
}
