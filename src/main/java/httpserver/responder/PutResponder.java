package httpserver.responder;

import httpserver.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.nio.file.Path;

public class PutResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        if (request.getPathString().equals("/form")) {
            return new OkResponse(new byte[0]);
        }
        return new MethodNotAllowedResponse();
    }
}
