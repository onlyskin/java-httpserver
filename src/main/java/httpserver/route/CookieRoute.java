package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.header.Header;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class CookieRoute extends Route {
    public CookieRoute() {
        super.routeString = "/cookie";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Response response = new OkResponse("Eat".getBytes());
        response.setHeader(new Header("Set-Cookie", "key=value"));
        return response;
    }
}
