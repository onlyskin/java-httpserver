package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.TeapotResponse;
import httpserver.response.Response;

public class CoffeeRoute extends Route {
    public CoffeeRoute() {
        super.routeString = "/coffee";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new TeapotResponse();
    }
}
