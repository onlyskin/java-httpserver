package httpserver;

import httpserver.header.Header;
import httpserver.response.Response;

import java.io.IOException;

import java.io.OutputStream;
import java.util.HashMap;

public class ResponseWriter {
    private final HashMap<Integer, String> statuses;
    private final OutputStream outputStream;
    private final Logger logger;

    public ResponseWriter(OutputStream outputStream, Logger logger) {
        this.outputStream = outputStream;
        this.logger = logger;
        this.statuses = new HashMap<>();
        statuses.put(200, "OK");
        statuses.put(204, "No Content");
        statuses.put(206, "Partial Content");
        statuses.put(302, "Found");
        statuses.put(400, "Bad Request");
        statuses.put(401, "Unauthorized");
        statuses.put(404, "Not Found");
        statuses.put(405, "Method Not Allowed");
        statuses.put(409, "Conflict");
        statuses.put(418, "I'm a teapot");
        statuses.put(500, "Internal Server Error");
    }

    public void write(Response response) {
        try {
            writeStatusCode(response);
            writeHeaders(response);
            writeEmptyLine();
            writePayload(response);
        } catch (IOException e) {
            logger.log(e.toString());
        }
    }

    private void writeStatusCode(Response response) throws IOException {
        String statusLine = getStatusLine(response.getStatusCode());
        write(statusLine.getBytes());
    }

    private String getStatusLine(int statusCode) {
        return ("HTTP/1.1 " + statusCode
                + " " + statuses.get(statusCode)
                + "\r\n");
    }

    private void writeHeaders(Response response) throws IOException {
        writeContentLengthHeader(response);
        for (Header header: response.getHeaders()) {
            write(getHeaderLineString(header).getBytes());
        }
    }

    private void writeContentLengthHeader(Response response) throws IOException {
        Header contentLengthHeader = response.getContentLengthHeader();
        write(getHeaderLineString(contentLengthHeader).getBytes());
    }

    private String getHeaderLineString(Header header) {
        return header.getKey() + ": " + header.getValue() + "\r\n";
    }

    private void writeEmptyLine() throws IOException {
        write(("\r\n").getBytes());
    }

    private void writePayload(Response response) throws IOException {
        response.writePayload(outputStream);
    }

    private void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }
}
