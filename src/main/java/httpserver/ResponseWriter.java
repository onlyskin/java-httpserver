package httpserver;

import httpserver.header.Header;
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
        statuses.put(500, "Internal Server Error");
        statuses.put(204, "No Content");
        statuses.put(409, "Conflict");
        statuses.put(206, "Partial Content");
    }

    public void write(Response response) {
        try {
            writeStatusCode(response);
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

    private void writeHeaders(Response response) throws IOException {
        writeContentLengthHeader(response);
        for (Header header: response.getHeaders()) {
            write((header.toString() + "\r\n").getBytes());
        }
    }

    private void writeContentLengthHeader(Response response) throws IOException {
        String header = response.getContentLengthHeader().toString() + "\r\n";
        write(header.getBytes());
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

    private String statusLine(int statusCode) {
        return ("HTTP/1.1 " + statusCode
                    + " " + statuses.get(statusCode)
                    + "\r\n");
    }

}
