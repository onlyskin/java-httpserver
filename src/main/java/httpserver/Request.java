package httpserver;

import java.util.HashMap;

public class Request {
    private final String method;
    private final String path;
    private final HashMap<String, String> headers;

    public Request(String method, String path, HashMap<String, String> headers) {
        this.method = method;
        this.path = path;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String,String> getHeaders() {
        return headers;
    }
}
