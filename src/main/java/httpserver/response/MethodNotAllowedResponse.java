package httpserver.response;

public class MethodNotAllowedResponse extends Response {
    public int getStatusCode() {
        return 405;
    }
}
