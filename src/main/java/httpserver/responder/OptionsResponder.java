package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.MethodResponderSupplier;
import httpserver.header.Header;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.util.List;
import java.util.StringJoiner;

public class OptionsResponder extends MethodResponder {
    private final MethodResponderSupplier methodResponderSupplier;

    public OptionsResponder(MethodResponderSupplier methodResponderSupplier) {
        this.methodResponderSupplier = methodResponderSupplier;
        super.methodString = "OPTIONS";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        List<MethodResponder> methodResponders = methodResponderSupplier.allResponders();
        StringJoiner joiner = new StringJoiner(",");

        for (MethodResponder methodResponder: methodResponders) {
            if (methodResponder.allows(request.getPathString())) {
                joiner.add(methodResponder.getMethodString());
            }
        }

        Response response = new OkResponse("".getBytes());
        response.setHeader(new Header("Allow", joiner.toString()));
        return response;
    }

    @Override
    public boolean allows(String pathString) {
        return true;
    }
}
