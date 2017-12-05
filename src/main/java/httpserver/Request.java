package httpserver;

import java.util.HashMap;

public class Request {
    private Method method;
    private final String path;
    private final HashMap<String, String> headers;

    public Request(String method, String path, HashMap<String, String> headers) {
        try {
            this.method = Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            this.method = Method.INVALID;
        }
        this.path = path;
        this.headers = headers;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String,String> getHeaders() {
        return headers;
    }
}
