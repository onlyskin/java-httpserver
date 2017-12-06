package httpserver;

import httpserver.responder.GeneralResponder;
import httpserver.response.NotFoundResponse;
import httpserver.response.Response;

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
        Response response;
        try {
            Request request = new RequestParser().parse(inputStream);
            response = new GeneralResponder().respond(root, request);
        } catch (Exception e) {
            response = new NotFoundResponse();
        }
        new ResponseWriter().write(response, outputStream);
    }
}
