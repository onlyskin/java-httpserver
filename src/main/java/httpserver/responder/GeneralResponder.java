package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;

import java.io.IOException;


public class GeneralResponder implements Responder {
    private final ResponderSupplier responderSupplier;

    public GeneralResponder(ResponderSupplier responderSupplier) {
        this.responderSupplier = responderSupplier;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Responder responder = responderSupplier.responderForMethodString(request.getMethodString());
        try {
            return responder.respond(appConfig, request);
        } catch (IOException e) {
            return new ServerErrorResponse();
        }
    }
}
