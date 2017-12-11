package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.FourEighteenResponse;
import httpserver.response.Response;

public class CoffeeResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new FourEighteenResponse();
    }
}
