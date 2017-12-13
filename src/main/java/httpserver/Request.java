package httpserver;

import httpserver.header.Header;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private final String method;
    private final String pathString;
    private final Header[] headers;
    private final String queryString;

    public Request(String method, String pathString, Header[] headers) {
        this(method, pathString, headers, "");
    }

    public Request(String method, String pathString, Header[] headers, String queryString) {
        this.method = method;
        this.pathString = pathString;
        this.headers = headers;
        this.queryString = queryString;
    }

    public String getMethodString() {
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

    public Parameter[] getParams() {
        List<Parameter> parameters = new ArrayList<>();
        String[] queryStrings = queryString.split("&");

        for (String queryString: queryStrings) {
            parameters.add(queryStringToParameter(queryString));
        }
        return parameters.toArray(new Parameter[0]);
    }

    private Parameter queryStringToParameter(String queryString) {
        String[] parts = queryString.split("=");
        return new Parameter(decode(parts[0]), decode(parts[1]));
    }

    private String decode(String input) {
        return new UrlDecoder().decode(input);
    }
}
