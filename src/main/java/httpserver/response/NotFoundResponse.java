package httpserver.response;

public class NotFoundResponse implements Response {
    @Override
    public int getStatusCode() {
        return 404;
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }
}
