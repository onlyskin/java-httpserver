package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.MethodResponderSupplier;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;

import java.io.IOException;

public class GeneralResponder {
    private final MethodResponderSupplier methodResponderSupplier;

    public GeneralResponder(MethodResponderSupplier methodResponderSupplier) {
        this.methodResponderSupplier = methodResponderSupplier;
    }

    public Response respond(AppConfig appConfig, Request request) {
        Responder responder = methodResponderSupplier.supplyResponder(request);
        try {
            return responder.respond(appConfig, request);
        } catch (IOException e) {
            return new ServerErrorResponse();
        }
    }
}
