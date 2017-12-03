package httpserver;

public class Response {
    private final int statusCode;
    private final byte[] payload;

    public Response(int statusCode, byte[] payload) {
        this.statusCode = statusCode;
        this.payload = payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public byte[] getPayload() {
        return payload;
    }
}
