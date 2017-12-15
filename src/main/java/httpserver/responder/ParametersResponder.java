package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class ParametersResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse(echoQueryString(request).getBytes());
    }

    @Override
    public boolean allowed(String pathString) {
        return pathString.equals("/parameters");
    }

    private String echoQueryString(Request request) {
        Parameter[] parameters = request.getParams();
        String payload = "";
        for (Parameter parameter: parameters) {
            payload = payload + parameter.getKey() + " = " + parameter.getValue() + "\r\n";
        }
        return payload;
    }

}
