package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RequestParser {
    public Request parse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));

        String[] requestParams = in.readLine().split(" ");

        HashMap<String, String> headers = new HashMap<>();
        String inputLine;
        while (!(inputLine = in.readLine()).equals("")) {
            String[] header = inputLine.split(":\\s*", 2);
            headers.put(header[0], header[1]);
        }

        return new Request(requestParams[0], requestParams[1], headers);
    }
}
