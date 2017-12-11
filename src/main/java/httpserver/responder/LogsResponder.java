package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Header;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class LogsResponder implements Responder {
    @Override
    public Response respond(AppConfig appConfig, Request request) {
        byte[] log = appConfig.getLogger().readLog();
        return new OkResponse(log, new Header[0]);
    }
}
