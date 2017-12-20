package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.header.Header;
import httpserver.responder.Responder;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class CookieResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Response response = new OkResponse("Eat".getBytes());
        response.setHeader(new Header("Set-Cookie", "key=value"));
        return response;
    }

    @Override
    public boolean handles(String pathString) {
        return pathString.equals("/cookie");
    }
}
