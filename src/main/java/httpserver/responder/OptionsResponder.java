package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
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
        super.method = Method.OPTIONS;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        List<MethodResponder> methodResponders = methodResponderSupplier.allResponders();
        StringJoiner joiner = new StringJoiner(",");

        for (MethodResponder methodResponder: methodResponders) {
            if (methodResponder.allows(request)) {
                joiner.add(methodResponder.getMethod().toString());
            }
        }

        Response response = new OkResponse("".getBytes());
        response.setHeader(new Header("Allow", joiner.toString()));
        return response;
    }

    public boolean allows(Request request) {
        return true;
    }
}
