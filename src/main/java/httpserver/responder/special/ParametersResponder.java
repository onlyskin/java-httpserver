package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.request.Request;
import httpserver.responder.Responder;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class ParametersResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse(echoQueryString(request).getBytes());
    }

    @Override
    public boolean allows(String pathString) {
        return pathString.equals("/parameters");
    }

    private String echoQueryString(Request request) {
        Parameter[] parameters = request.getParams();
        StringBuilder stringBuilder = new StringBuilder();
        for (Parameter parameter: parameters) {
            stringBuilder.append(parameter.getKey());
            stringBuilder.append(" = ");
            stringBuilder.append(parameter.getValue());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

}
