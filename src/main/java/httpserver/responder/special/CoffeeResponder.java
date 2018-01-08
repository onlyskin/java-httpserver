package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.responder.Responder;
import httpserver.response.TeapotResponse;
import httpserver.response.Response;

public class CoffeeResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new TeapotResponse();
    }

    @Override
    public boolean allows(String pathString) {
        return pathString.equals("/coffee");
    }
}
