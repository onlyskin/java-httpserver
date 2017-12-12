package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class CookieResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse("Eat".getBytes());
    }
}
