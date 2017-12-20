package httpserver.response;

import httpserver.header.ContentLengthHeader;
import httpserver.header.Header;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Response {
    private byte[] payload;
    private final List<Header> headers;
    private boolean isHead;

    public Response() {
        this.payload = new byte[0];
        this.headers = new ArrayList<>();
        this.isHead = false;
    }

    public abstract int getStatusCode();

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

    public void setHeadTrue() {
        isHead = true;
    }

    public void writePayload(OutputStream outputStream) throws IOException {
        if (isHead) {
            outputStream.write(new byte[0]);
        }
        outputStream.write(payload);
    }
}
