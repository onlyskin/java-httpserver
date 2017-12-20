package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.responder.Responder;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class TeaResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse("".getBytes());
    }

    @Override
    public boolean handles(String pathString) {
        return pathString.equals("/tea");
    }
}
