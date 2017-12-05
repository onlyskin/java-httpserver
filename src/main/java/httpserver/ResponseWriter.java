package httpserver;

import httpserver.response.Response;

import java.io.IOException;

import java.io.OutputStream;
import java.util.HashMap;

public class ResponseWriter {
    private final HashMap<Integer, String> statuses;

    public ResponseWriter() {
        this.statuses = new HashMap<>();
        statuses.put(200, "OK");
        statuses.put(404, "Not Found");
        statuses.put(405, "Method Not Allowed");
    }

    public void write(Response response, OutputStream outputStream) throws IOException {
        outputStream.write(statusLine(response.getStatusCode()).getBytes());
        outputStream.write(contentLengthHeader(response.getPayload()).getBytes());
        outputStream.write(("\r\n").getBytes());
        outputStream.write(response.getPayload());
    }

    private String contentLengthHeader(byte[] payload) {
        return "Content-Length: " + payload.length + "\r\n";
    }

    private String statusLine(int statusCode) {
        return ("HTTP/1.1 " + statusCode
                    + " " + statuses.get(statusCode)
                    + "\r\n");
    }

}
