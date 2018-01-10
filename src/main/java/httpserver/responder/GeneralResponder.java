package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.MethodResponderSupplier;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;

import java.io.IOException;

public class GeneralResponder {
    private final MethodResponderSupplier methodResponderSupplier;

    public GeneralResponder(MethodResponderSupplier methodResponderSupplier) {
        this.methodResponderSupplier = methodResponderSupplier;
    }

    public Response respond(AppConfig appConfig, Request request) {
        MethodResponder responder;

        try {
            responder = methodResponderSupplier.supplyResponder(request);
        } catch (Exception e) {
            return new MethodNotAllowedResponse();
        }

        if (!responder.allows(request)) {
            return new MethodNotAllowedResponse();
        }

        try {
            return responder.respond(appConfig, request);
        } catch (IOException e) {
            return new ServerErrorResponse();
        }
    }
}
