package httpserver.response;

public class MethodNotAllowedResponse implements Response {
    @Override
    public int getStatusCode() {
        return 405;
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }
}
