package httpserver.response;

import httpserver.header.ContentLengthHeader;
import httpserver.header.Header;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Response {
    private byte[] payload;
    private InputStream payloadStream;
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

    public InputStream getPayloadStream() {
        return payloadStream;
    }

    public void setPayloadStream(InputStream payloadStream) {
        this.payloadStream = payloadStream;
    }

    public Header[] getHeaders() {
        return headers.toArray(new Header[0]);
    }

    public void setHeader(Header header) {
        headers.add(header);
    }

    public Header getContentLengthHeader() {
        if (payloadStream != null) {
            int data = payloadStream.read();
            while(data != -1){
                System.out.print((char) data);
                outputStream.write(data);
                data = payloadStream.read();
            }
        }
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

    public void writePayloadStream(OutputStream outputStream) throws IOException {
        int data = payloadStream.read();
        while(data != -1){
            System.out.print((char) data);
            outputStream.write(data);
            data = payloadStream.read();
        }
    }
}
