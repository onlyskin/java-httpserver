package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.header.Header;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.util.StringJoiner;

public class OptionsResponder implements Responder {
    private final ResponderSupplier responderSupplier;

    public OptionsResponder(ResponderSupplier responderSupplier) {
        this.responderSupplier = responderSupplier;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Method[] methods = Method.values();
        StringJoiner joiner = new StringJoiner(",");

        for (Method method: methods) {
            Responder responder = responderSupplier.responderForMethodString(method.toString());
            if (responder.handles(request.getPathString())) {
                joiner.add(method.toString());
            }
        }

        Response response = new OkResponse("".getBytes());
        response.setHeader(new Header("Allow", joiner.toString()));
        return response;
    }

    @Override
    public boolean handles(String pathString) {
        return true;
    }
}
