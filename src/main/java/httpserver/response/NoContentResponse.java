package httpserver.response;

import httpserver.header.EtagHeader;

public class NoContentResponse extends Response {
    public NoContentResponse(String hash) {
        super.setHeader(new EtagHeader(hash));
    }

    @Override
    public int getStatusCode() {
        return 204;
    }
}
