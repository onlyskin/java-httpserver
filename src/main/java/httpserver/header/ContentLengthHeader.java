package httpserver.header;

public class ContentLengthHeader extends Header {
    public ContentLengthHeader(byte[] payload) {
        super("Content-Length", String.valueOf(payload.length));
    }
}
