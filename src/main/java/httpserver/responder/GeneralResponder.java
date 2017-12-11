package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.response.Response;


public class GeneralResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return request.getMethod().respond(appConfig, request);
    }
}
