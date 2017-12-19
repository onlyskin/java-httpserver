package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.responder.Responder;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class EatCookieResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Response response = new OkResponse("mmmm chocolate".getBytes());
        return response;
    }

    @Override
    public boolean allowed(String pathString) {
        return pathString.equals("/eat_cookie");
    }
}
