package httpserver;

import java.util.HashMap;

public class Request {
    private final Method method;
    private final String path;
    private final HashMap<String, String> headers;

    public Request(String method, String path, HashMap<String, String> headers) {
        this.method = Method.valueOf(method);
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
