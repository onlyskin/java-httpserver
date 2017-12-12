package httpserver.response;

import httpserver.ContentLengthHeader;
import httpserver.Header;

import java.util.ArrayList;
import java.util.List;

public abstract class Response {
    private byte[] payload;
    private final List<Header> headers;

    public Response() {
        this.payload = new byte[0];
        this.headers = new ArrayList<>();
    }

    public abstract int getStatusCode();

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] newPayload) {
        payload = newPayload;
    }

    public Header[] getHeaders() {
        return headers.toArray(new Header[0]);
    }

    public void setHeader(Header header) {
        headers.add(header);
    }

    public Header getContentLengthHeader() {
        return new ContentLengthHeader(payload);
    }
}
