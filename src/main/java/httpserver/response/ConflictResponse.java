package httpserver.response;

public class ConflictResponse extends Response {
    @Override
    public int getStatusCode() {
        return 409;
    }
}
