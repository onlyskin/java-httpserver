package httpserver;

import httpserver.responder.GeneralResponder;
import httpserver.response.NotFoundResponse;
import httpserver.response.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SocketHandler implements Runnable {
    private final Path root;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SocketHandler(Path root, InputStream inputStream, OutputStream outputStream) {
        this.root = root;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run() {
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
