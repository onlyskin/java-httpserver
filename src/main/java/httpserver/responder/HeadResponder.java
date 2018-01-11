package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.RangeHeaderValueParser;
import httpserver.response.Response;
import httpserver.route.Router;

import java.io.IOException;

public class HeadResponder extends GetResponder {
    public HeadResponder(Router getRouter,
                         PathExaminer pathExaminer,
                         Html html,
                         RangeHeaderValueParser rangeHeaderValueParser) {
        super(getRouter, pathExaminer, html, rangeHeaderValueParser);
        super.method= Method.HEAD;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Response response = super.respond(appConfig, request);
        response.setHeadTrue();
        return response;
    }
}
