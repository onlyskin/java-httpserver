package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.FoundResponse;
import httpserver.response.Response;

import java.io.IOException;

public class RedirectResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        return new FoundResponse("/");
    }

    @Override
    public boolean allowed(String pathString) {
        return pathString.equals("/redirect");
    }
}
