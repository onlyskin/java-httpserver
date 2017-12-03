package httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.HashMap;

public class ResponseWriter {
    private final HashMap<Integer, String> statuses;

    public ResponseWriter() {
        this.statuses = new HashMap<>();
        statuses.put(200, "OK");
        statuses.put(404, "Not Found");
    }

    public void write(Response response, ByteArrayOutputStream outputStream) throws IOException {
        int statusCode = response.getStatusCode();
        outputStream.write(("HTTP/1.1 " + statusCode
                    + " " + statuses.get(statusCode)
                    + "\r\n").getBytes());
    }
}
