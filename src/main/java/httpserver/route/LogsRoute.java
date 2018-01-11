package httpserver.route;

import httpserver.AppConfig;
import httpserver.Authorizer;
import httpserver.request.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.response.ServerErrorResponse;
import httpserver.response.UnauthorizedResponse;

import java.io.IOException;

public class LogsRoute extends Route {
    public LogsRoute() {
        super.routeString = "/logs";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Authorizer authorizer = new Authorizer();

        if (!authorizer.authorize(request)) {
            return new UnauthorizedResponse();
        }

        try {
            byte[] log = appConfig.getLogger().readLog();
            return new OkResponse(log);
        } catch (IOException e) {
            return new ServerErrorResponse();
        }
    }
}
