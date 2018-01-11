package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.FoundResponse;
import httpserver.response.Response;

import java.io.IOException;

public class RedirectRoute extends Route {
    public RedirectRoute() {
        super.routeString = "/redirect";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        return new FoundResponse("/");
    }
}
