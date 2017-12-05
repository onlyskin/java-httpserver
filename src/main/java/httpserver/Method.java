package httpserver;

import httpserver.responder.GetResponder;
import httpserver.responder.Responder;

import java.nio.file.Path;

public enum Method {
    GET(new GetResponder()),
    HEAD(null),
    POST(null),
    PUT(null),
    DELETE(null),
    OPTIONS(null);

    private final Responder responder;

    Method(Responder responder) {
        this.responder = responder;
    }

    public Response respond(Path root, Request request) {
        return responder.respond(root, request);
    }
}
