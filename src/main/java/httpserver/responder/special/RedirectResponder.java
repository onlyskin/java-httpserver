package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.responder.Responder;
import httpserver.response.FoundResponse;
import httpserver.response.Response;

import java.io.IOException;

public class RedirectResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        return new FoundResponse("/");
    }

    @Override
    public boolean allows(String pathString) {
        return pathString.equals("/redirect");
    }
}
