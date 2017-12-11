package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Authorizer;
import httpserver.Header;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.response.UnauthorizedResponse;

public class LogsResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        if (new Authorizer().authorize(request)) {
            byte[] log = appConfig.getLogger().readLog();
            return new OkResponse(log, new Header[0]);
        } else {
            return new UnauthorizedResponse();
        }
    }
}
