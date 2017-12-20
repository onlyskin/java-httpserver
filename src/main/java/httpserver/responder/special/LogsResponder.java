package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.Authorizer;
import httpserver.Request;
import httpserver.responder.Responder;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.response.UnauthorizedResponse;

public class LogsResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        Authorizer authorizer = new Authorizer();

        if (!authorizer.authorize(request)) {
            return new UnauthorizedResponse();
        }

        byte[] log = appConfig.getLogger().readLog();
        return new OkResponse(log);
    }

    @Override
    public boolean handles(String pathString) {
        return pathString.equals("/logs");
    }
}
