package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.responder.Responder;
import httpserver.response.FourEighteenResponse;
import httpserver.response.Response;

public class CoffeeResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new FourEighteenResponse();
    }

    @Override
    public boolean allowed(String pathString) {
        return pathString.equals("/coffee");
    }
}
