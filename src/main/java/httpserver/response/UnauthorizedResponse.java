package httpserver.response;

import httpserver.Header;

public class UnauthorizedResponse extends Response {
    public UnauthorizedResponse() {
        super.setHeader(new Header("WWW-Authenticate", "Basic realm=\"\""));
    }

    public int getStatusCode() {
        return 401;
    }
}
