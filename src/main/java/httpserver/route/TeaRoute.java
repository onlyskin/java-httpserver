package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class TeaRoute extends Route {
    public TeaRoute() {
        super.routeString = "/tea";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse("".getBytes());
    }
}
