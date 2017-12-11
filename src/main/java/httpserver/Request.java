package httpserver;

public class Request {
    private Method method;
    private final String pathString;
    private final Header[] headers;

    public Request(String method, String pathString, Header[] headers) {
        try {
            this.method = Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            this.method = Method.INVALID;
        }
        this.pathString = pathString;
        this.headers = headers;
    }

    public Method getMethod() {
        return method;
    }

    public String getPathString() {
        return pathString;
    }

    public Header[] getHeaders() {
        return headers;
    }
}
