package httpserver.response;

public class OkResponse implements Response {
    private final byte[] payload;

    public OkResponse(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public int getStatusCode() {
        return 200;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }
}
