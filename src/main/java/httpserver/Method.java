package httpserver;

import httpserver.responder.*;
import httpserver.response.Response;

public enum Method {
    GET(new GetResponder()),
    POST(new PostResponder()),
    PUT(new PutResponder()),
    INVALID(new InvalidMethodResponder());

    private final Responder responder;

    Method(Responder responder) {
        this.responder = responder;
    }

    public Response respond(AppConfig root, Request request) {
        return responder.respond(root, request);
    }
}
