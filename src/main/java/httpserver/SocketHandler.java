package httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SocketHandler {
    private final RequestParser requestParser;
    private final Responder responder;
    private final ResponseWriter responseWriter;

    public SocketHandler(Path root) {
        this.requestParser = new RequestParser();
        this.responder = new Responder(root);
        this.responseWriter = new ResponseWriter();
    }

    public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
        Request request = requestParser.parse(inputStream);
        Response response = responder.makeResponse(request);
        responseWriter.write(response, outputStream);
    }
}
