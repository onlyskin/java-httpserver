package httpserver.responder;

import httpserver.NotFoundResponse;
import httpserver.Request;
import httpserver.OkResponse;
import httpserver.Response;

import java.nio.file.Path;

public class PostResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        if (request.getPath() == "/form") {
            return new OkResponse(new byte[0]);
        }
        return new NotFoundResponse();
    }
}
