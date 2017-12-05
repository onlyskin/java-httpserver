package httpserver.response;

public interface Response {
    int getStatusCode();

    byte[] getPayload();
}
