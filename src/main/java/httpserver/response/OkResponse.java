package httpserver.response;

public class OkResponse extends Response {

    public OkResponse(byte[] payload) {
        super.setPayload(payload);
    }

    public int getStatusCode() {
        return 200;
    }
}
