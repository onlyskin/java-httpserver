package httpserver.responder;

import httpserver.Request;
import httpserver.response.Response;

import java.nio.file.Path;

public class GeneralResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        return request.getMethod().respond(root, request);
    }
}
