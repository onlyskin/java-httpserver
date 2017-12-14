package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;

import java.io.IOException;

public interface Responder {
    Response respond(AppConfig appConfig, Request request) throws IOException;
}
