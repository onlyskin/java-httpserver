package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.IOException;

public interface Responder {
    Response respond(AppConfig appConfig, Request request) throws IOException;

    boolean handles(String pathString);
}
