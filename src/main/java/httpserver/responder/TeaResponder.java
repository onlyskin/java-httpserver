package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class TeaResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse("".getBytes());
    }

    @Override
    public boolean allowed(String pathString) {
        return pathString.equals("/tea");
    }
}
