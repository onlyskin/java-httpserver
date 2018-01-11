package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class EatCookieRoute extends Route {
    public EatCookieRoute() {
        super.routeString = "/eat_cookie";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Response response = new OkResponse("mmmm chocolate".getBytes());
        return response;
    }
}
