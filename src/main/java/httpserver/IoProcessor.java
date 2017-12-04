package httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoProcessor {
    private final RequestParser requestParser;
    private final Responder responder;
    private final ResponseWriter responseWriter;

    public IoProcessor(String fileDirectory) {
        this.requestParser = new RequestParser();
        this.responder = new Responder(fileDirectory);
        this.responseWriter = new ResponseWriter();
    }

    public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
        Request request = requestParser.parse(inputStream);
        Response response = responder.makeResponse(request);
        responseWriter.write(response, outputStream);
    }
}
