package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.response.Response;

import java.io.IOException;

public class HeadResponder extends GetResponder {
    public HeadResponder(RouteMap getRouteMap, PathExaminer pathExaminer, Html html) {
        super(getRouteMap, pathExaminer, html);
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Response response = super.respond(appConfig, request);
        response.setHeadTrue();
        return response;
    }
}
