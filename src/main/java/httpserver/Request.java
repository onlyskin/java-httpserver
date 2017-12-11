package httpserver;

import java.util.HashMap;

public class Request {
    private Method method;
    private final String pathString;
    private final HashMap<String, String> headers;

    public Request(String method, String pathString, HashMap<String, String> headers) {
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

    public HashMap<String,String> getHeaders() {
        return headers;
    }
}
