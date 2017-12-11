package httpserver;

public class Request {
    private Method method;
    private final String pathString;
    private final Header[] headers;
    private final String queryString;

    public Request(String method, String pathString, Header[] headers) {
        this(method, pathString, headers, "");
    }

    public Request(String method, String pathString, Header[] headers, String queryString) {
        try {
            this.method = Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            this.method = Method.INVALID;
        }
        this.pathString = pathString;
        this.headers = headers;
        this.queryString = queryString;
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

    public String getHeaderValue(String key) {
        for (Header header: headers) {
            if (header.getKey().equals(key)) {
                return header.getValue();
            }
        }
        return null;
    }

    public boolean hasHeader(String key) {
        if (getHeaderValue(key) == null) {
            return false;
        }
        return true;
    }

    public String getQueryString() {
        return queryString;
    }
}
