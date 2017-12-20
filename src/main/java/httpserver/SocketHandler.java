package httpserver;

import httpserver.responder.GeneralResponder;
import httpserver.response.BadRequestResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.io.InputStream;

public class SocketHandler implements Runnable {
    private final AppConfig appConfig;
    private final InputStream inputStream;
    private final RequestParser requestParser;
    private final GeneralResponder generalResponder;
    private final ResponseWriter responseWriter;

    public SocketHandler(AppConfig appConfig,
                         InputStream inputStream,
                         RequestParser requestParser,
                         GeneralResponder generalResponder,
                         ResponseWriter responseWriter) {
        this.appConfig = appConfig;
        this.inputStream = inputStream;
        this.requestParser = requestParser;
        this.generalResponder = generalResponder;
        this.responseWriter = responseWriter;
    }

    public void run() {
        Response response;

        try {
            Request request = requestParser.parse(inputStream);
            response = generalResponder.respond(appConfig, request);
        } catch (Exception e) {
            response = new BadRequestResponse();
        }

        responseWriter.write(response);
        try {
            inputStream.close();
        } catch (IOException e) {
            System.out.println("The inputStream could not be closed.");
        }
    }
}
