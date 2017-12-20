package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.Response;

public class InvalidMethodResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new MethodNotAllowedResponse();
    }

    @Override
    public boolean handles(String pathString) {
        return true;
    }
}
