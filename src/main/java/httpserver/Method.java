package httpserver;

import httpserver.responder.*;
import httpserver.response.Response;

import java.nio.file.Path;

public enum Method {
    GET(new GetResponder()),
    POST(new PostResponder()),
    PUT(new PutResponder()),
    INVALID(new InvalidMethodResponder());

    private final Responder responder;

    Method(Responder responder) {
        this.responder = responder;
    }

    public Response respond(Path root, Request request) {
        return responder.respond(root, request);
    }
}
