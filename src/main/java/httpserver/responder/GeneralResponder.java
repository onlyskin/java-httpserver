package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.response.Response;


public class GeneralResponder implements Responder {
    private final ResponderSupplier responderSupplier;

    public GeneralResponder(ResponderSupplier responderSupplier) {
        this.responderSupplier = responderSupplier;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Responder responder = responderSupplier.responderForMethodString(request.getMethodString());
        return responder.respond(appConfig, request);
    }
}
