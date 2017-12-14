package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.Request;
import httpserver.ResponderSupplier;
import httpserver.header.Header;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.util.StringJoiner;

public class OptionsResponder implements Responder {
    private final ResponderSupplier responderSupplier;

    public OptionsResponder(ResponderSupplier responderSupplier) {
        this.responderSupplier = responderSupplier;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Method[] methods = Method.values();
        StringJoiner joiner = new StringJoiner(",");
        for (Method method: methods) {
            Responder responder = responderSupplier.responderForMethodString(method.toString());
            if (responder.allowed(request.getPathString())) {
                joiner.add(method.toString());
            }
        }
        Response response = new OkResponse("".getBytes());
        response.setHeader(new Header("Allow", joiner.toString()));
        return response;
    }

    @Override
    public boolean allowed(String pathString) {
        return true;
    }
}
