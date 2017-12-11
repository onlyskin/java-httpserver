package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private final Logger logger;

    public RequestParser(AppConfig appConfig) {
        this.logger = appConfig.getLogger();
    }

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(inputStream));

        String firstLine = in.readLine();
        logger.log(firstLine);

        String[] parts = parseFirstLine(firstLine);
        String method = parts[0];
        String path = parts[1];
        String queryString = parts[2];

        Header[] headers = parseHeaders(in);

        return new Request(method, path, headers, queryString);
    }

    private String[] parseFirstLine(String firstLine) {
        String[] parts = firstLine.split(" ");
        String method = parts[0];
        String path = pathToParts(parts[1])[0];
        String queryString = pathToParts(parts[1])[1];
        return new String[]{method, path, queryString};
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
        return new Header(parts[0], parts[1]);
    }
}
