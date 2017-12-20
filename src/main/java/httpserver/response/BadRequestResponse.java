package httpserver.response;

public class BadRequestResponse extends Response {
    @Override
    public int getStatusCode() {
        return 400;
    }
}
