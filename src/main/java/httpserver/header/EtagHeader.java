package httpserver.header;

public class EtagHeader extends Header {
    public EtagHeader(String etag) {
        super("ETag", etag);
    }
}
