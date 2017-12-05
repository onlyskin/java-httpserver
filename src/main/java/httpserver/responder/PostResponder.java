package httpserver.responder;

import httpserver.Request;
import httpserver.Response;

import java.nio.file.Path;

public class PostResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        return new Response(200, new byte[0]);
    }
}
