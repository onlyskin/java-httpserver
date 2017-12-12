package httpserver.response;

public class NotFoundResponse extends Response {
    public int getStatusCode() {
        return 404;
    }
}
