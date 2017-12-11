package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private final Logger logger;

    public RequestParser(Logger logger) {
        this.logger = logger;
    }

    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(inputStream));

        String firstLine = in.readLine();
        logger.log(firstLine);

        String[] requestParams = firstLine.split(" ");
        Header[] headers = parseHeaders(in);

        return new Request(requestParams[0], requestParams[1], headers);
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
