package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class ParametersResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse(echoQueryString(request).getBytes());
    }

    private String echoQueryString(Request request) {
        String result = request.getQueryString();
        result = result.replace("&", "\r\n");
        result = result.replace("=", " = ");
        return result;
    }
}
