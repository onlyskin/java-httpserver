package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.ResponderSupplier;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;

import java.io.IOException;

public class GeneralResponder {
    private final ResponderSupplier responderSupplier;

    public GeneralResponder(ResponderSupplier responderSupplier) {
        this.responderSupplier = responderSupplier;
    }

    public Response respond(AppConfig appConfig, Request request) {
        Responder responder = responderSupplier.supplyResponder(request);
        try {
            return responder.respond(appConfig, request);
        } catch (IOException e) {
            return new ServerErrorResponse();
        }
    }
}
