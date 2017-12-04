package httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Server {
    private final String fileDirectory;
    private final RequestParser requestParser;
    private final Responder responder;
    private final ResponseWriter responseWriter;

    public Server(String fileDirectory) {
        this.fileDirectory = fileDirectory;
        this.requestParser = new RequestParser();
        this.responder = new Responder(fileDirectory);
        this.responseWriter = new ResponseWriter();
    }

    public void process(InputStream inputStream, ByteArrayOutputStream outputStream) throws IOException {
        Request request = requestParser.parse(inputStream);
        Response response = responder.makeResponse(request);
        responseWriter.write(response, outputStream);
    }
}
