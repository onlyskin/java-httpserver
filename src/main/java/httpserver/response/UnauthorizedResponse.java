package httpserver.response;

import httpserver.Header;

public class UnauthorizedResponse implements Response {
    @Override
    public int getStatusCode() {
        return 401;
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public Header[] getHeaders() {
        return new Header[]{new Header("WWW-Authenticate", "Basic realm=\"\"")};
    }
}
