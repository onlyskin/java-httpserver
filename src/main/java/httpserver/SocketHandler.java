package httpserver;

import httpserver.request.Request;
import httpserver.request.RequestParser;
import httpserver.responder.Responder;
import httpserver.response.BadRequestResponse;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.io.InputStream;

public class SocketHandler implements Runnable {
    private final AppConfig appConfig;
    private final InputStream inputStream;
    private final RequestParser requestParser;
    private final Responder responder;
    private final ResponseWriter responseWriter;

    public SocketHandler(AppConfig appConfig,
                         InputStream inputStream,
                         RequestParser requestParser,
                         Responder responder,
                         ResponseWriter responseWriter) {
        this.appConfig = appConfig;
        this.inputStream = inputStream;
        this.requestParser = requestParser;
        this.responder = responder;
        this.responseWriter = responseWriter;
    }

    public void run() {
        Response response;

        try {
            Request request = requestParser.parse(inputStream);
            response = responder.respond(appConfig, request);
        } catch (IllegalArgumentException e) {
            response = new MethodNotAllowedResponse();
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
