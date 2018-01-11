package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.IOException;

public abstract class Route {
    String routeString;

    public abstract Response respond(AppConfig appConfig, Request request) throws IOException;

    boolean allows(Request request) {
        return request.getPathString().equals(routeString);
    }
}
