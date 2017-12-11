package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;

public interface Responder {
    Response respond(AppConfig appConfig, Request request);
}
