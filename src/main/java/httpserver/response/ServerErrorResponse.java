package httpserver.response;

public class ServerErrorResponse extends Response {
    @Override
    public int getStatusCode() {
        return 500;
    }
}
