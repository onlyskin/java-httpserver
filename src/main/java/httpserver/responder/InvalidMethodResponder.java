package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.Response;

public class InvalidMethodResponder extends MethodResponder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new MethodNotAllowedResponse();
    }

    @Override
    public boolean allows(String pathString) {
        return true;
    }

    @Override
    public boolean handles(Request request) {
        return true;
    }
}
