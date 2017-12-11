package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class PutResponder implements Responder {
    @Override
    public Response respond(AppConfig appconfig, Request request) {
        if (request.getPathString().equals("/form")) {
            return new OkResponse(new byte[0]);
        }
        return new MethodNotAllowedResponse();
    }
}
