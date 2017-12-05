package httpserver.responder;

import httpserver.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.Response;

import java.nio.file.Path;

public class InvalidMethodResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        return new MethodNotAllowedResponse();
    }
}
