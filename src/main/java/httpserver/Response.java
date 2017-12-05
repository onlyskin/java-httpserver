package httpserver;

public interface Response {
    int getStatusCode();

    byte[] getPayload();
}
