package httpserver;

import httpserver.responder.GeneralResponder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SocketHandler {
    private final Path root;

    public SocketHandler(Path root) {
        this.root = root;
    }

    public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
        Request request = new RequestParser().parse(inputStream);
        Response response = new GeneralResponder().respond(root, request);
        new ResponseWriter().write(response, outputStream);
    }
}
