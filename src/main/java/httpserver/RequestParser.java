package httpserver;

import httpserver.header.Header;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private final Logger logger;
    private int contentLength;

    public RequestParser(AppConfig appConfig) {
        this.logger = appConfig.getLogger();
    }

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String firstLine = bufferedReader.readLine();

        String[] parts = parseFirstLine(firstLine);
        String method = parts[0];
        String path = parts[1];
        String queryString = parts[2];

        Header[] headers = parseHeaders(bufferedReader);
        log(firstLine, headers);

        String body = getBody(bufferedReader);

        return new Request(method, path, headers, queryString, body);
    }

    private String getBody(BufferedReader bufferedReader) throws IOException {
        String output = "";
        int counter = 0;
        while (counter < contentLength) {
            output = output + (char)bufferedReader.read();
            counter = counter + 1;
        }
        return output;
    }

    private void log(String firstLine, Header[] headers) {
        logger.log(firstLine);
        for (Header header: headers) {
            logger.log(header.toString());
        }
        logger.log("");
    }

    private String[] parseFirstLine(String firstLine) {
        String[] parts = firstLine.split(" ");
        String method = parts[0];
        String path = pathToParts(parts[1])[0];
        String queryString = pathToParts(parts[1])[1];
        return new String[]{method, new UrlDecoder().decode(path), queryString};
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

    private Header[] parseHeaders(BufferedReader in) throws IOException {
        List<Header> headers = new ArrayList<>();
        String inputLine;

        while (!(inputLine = in.readLine()).equals("")) {
            Header header = parseHeader(inputLine);
            headers.add(header);
        }

        return headers.toArray(new Header[0]);
    }

    private Header parseHeader(String inputLine) {
        String[] parts = inputLine.split(":\\s*", 2);
        if (parts[0].equals("Content-Length")) {
            contentLength = Integer.parseInt(parts[1]);
        }
        return new Header(parts[0], parts[1]);
    }
}
