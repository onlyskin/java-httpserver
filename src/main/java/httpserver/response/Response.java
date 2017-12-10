package httpserver.response;

import httpserver.Header;

public interface Response {
    int getStatusCode();

    byte[] getPayload();

    Header[] getHeaders();
}
