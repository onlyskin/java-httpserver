package httpserver.request;

import httpserver.AppConfig;
import httpserver.Logger;
import httpserver.Method;
import httpserver.header.Header;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private final Logger logger;
    private int contentLength;

    public RequestParser(AppConfig appConfig) {
        this.logger = appConfig.getLogger();
        this.contentLength = 0;
    }

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = getRequestLine(bufferedReader);

        try {
            Method.valueOf(requestLine.getMethod());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }

        Header[] headers = getHeaders(bufferedReader);

        String body = getBody(bufferedReader);

        return new Request(Method.valueOf(requestLine.getMethod()),
                requestLine.getPath(),
                headers,
                requestLine.getQueryString(),
                body);
    }

    private RequestLine getRequestLine(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        logger.log(firstLine);
        return parseRequestLine(firstLine);
    }

    private class RequestLine {
        private final String method;
        private final String path;
        private final String queryString;

        private String getMethod() {
            return method;
        }

        private String getPath() {
            return path;
        }

        private String getQueryString() {
            return queryString;
        }

        private RequestLine(String method, String path, String queryString) {
            this.method = method;
            this.path = path;
            this.queryString = queryString;
        }
    }

    private RequestLine parseRequestLine(String firstLine) {
        String[] parts = firstLine.split(" ");
        String method = parts[0];
        String path = pathToParts(parts[1])[0];
        String queryString = pathToParts(parts[1])[1];
        return new RequestLine(method, new UrlDecoder().decode(path), queryString);
    }

    private String[] pathToParts(String pathString) {
        String[] pathParts;
        if (pathString.contains("?")) {
            pathParts = pathString.split("\\?");
        } else {
            pathParts = new String[]{pathString, ""};
        }
        return pathParts;
    }

    private Header[] getHeaders(BufferedReader in) throws IOException {
        List<Header> headers = new ArrayList<>();
        String inputLine;

        while (!(inputLine = in.readLine()).equals("")) {
            logger.log(inputLine);
            Header header = parseHeader(inputLine);
            headers.add(header);
        }
        logger.log("\r\n");

        return headers.toArray(new Header[0]);
    }

    private Header parseHeader(String inputLine) {
        String[] parts = inputLine.split(":\\s*", 2);
        if (parts[0].equals("Content-Length")) {
            contentLength = Integer.parseInt(parts[1]);
        }
        return new Header(parts[0], parts[1]);
    }

    private String getBody(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        while (counter < contentLength) {
            stringBuilder.append((char)bufferedReader.read());
            counter = counter + 1;
        }
        return stringBuilder.toString();
    }
}
