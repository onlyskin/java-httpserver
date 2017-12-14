package httpserver.response;

import httpserver.header.Header;

public class FoundResponse extends Response {
    public FoundResponse(String location) {
        super.setHeader(new Header("Location", location));
    }

    @Override
    public int getStatusCode() {
        return 302;
    }
}
