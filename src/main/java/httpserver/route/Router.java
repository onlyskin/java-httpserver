package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;

import java.io.IOException;

public class Router {

    private final Route[] routes;

    public Router(Route[] routes) {
        this.routes = routes;
    }

    public boolean canRespond(Request request) {
        for (Route route : routes) {
            if (route.allows(request)) {
                return true;
            }
        }
        return false;
    }

    private Route routeForRequest(Request request) {
        for (Route route : routes) {
            if (route.allows(request)) {
                return route;
            }
        }
        return null;
    }

    public Response respond(AppConfig appConfig, Request request) {
        Route route = routeForRequest(request);
        try {
            return route.respond(appConfig, request);
        } catch (IOException | NullPointerException e) {
            return new ServerErrorResponse();
        }
    }
}
