package httpserver;

import httpserver.responder.GeneralResponder;
import httpserver.response.NotFoundResponse;
import httpserver.response.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SocketHandler implements Runnable {
    private final AppConfig appConfig;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SocketHandler(Path root, Logger logger, InputStream inputStream, OutputStream outputStream) {
        this.appConfig = new AppConfig(root, logger);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run() {
        Response response;

        try {
            Request request = new RequestParser(appConfig).parse(inputStream);
            response = new GeneralResponder().respond(appConfig, request);
        } catch (Exception e) {
            response = new NotFoundResponse();
        }

        new ResponseWriter(outputStream).write(response);
    }
}
