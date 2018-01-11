package httpserver.route;

import httpserver.AppConfig;
import httpserver.Parameter;
import httpserver.request.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;

public class ParametersRoute extends Route {
    public ParametersRoute() {
        super.routeString = "/parameters";
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) {
        return new OkResponse(echoQueryString(request).getBytes());
    }

    private String echoQueryString(Request request) {
        Parameter[] parameters = request.getParams();
        StringBuilder stringBuilder = new StringBuilder();
        for (Parameter parameter: parameters) {
            stringBuilder.append(parameter.getKey());
            stringBuilder.append(" = ");
            stringBuilder.append(parameter.getValue());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

}
