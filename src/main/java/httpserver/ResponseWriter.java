package httpserver;

import httpserver.response.Response;

import java.io.IOException;

import java.io.OutputStream;
import java.util.HashMap;

public class ResponseWriter {
    private final HashMap<Integer, String> statuses;
    private final OutputStream outputStream;

    public ResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.statuses = new HashMap<>();
        statuses.put(200, "OK");
        statuses.put(404, "Not Found");
        statuses.put(405, "Method Not Allowed");
        statuses.put(418, "I'm a teapot");
    }

    public void write(Response response) {
        try {
            writeStatusCode(response);
            writeContentLengthHeader(response);
            writeHeaders(response);
            writeEmptyLine();
            writePayload(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeStatusCode(Response response) throws IOException {
        write(statusLine(response.getStatusCode()).getBytes());
    }

    private void writeContentLengthHeader(Response response) throws IOException {
        write(contentLengthHeader(response.getPayload()).getBytes());
    }

    private void writeHeaders(Response response) throws IOException {
        for (Header header: response.getHeaders()) {
            write((header.toString() + "\r\n").getBytes());
        }
    }

    private void writeEmptyLine() throws IOException {
        write(("\r\n").getBytes());
    }

    private void writePayload(Response response) throws IOException {
        write(response.getPayload());
    }

    private void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
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
