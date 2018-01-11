package httpserver.route;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class FormGetRoute extends Route {
    private final PathExaminer pathExaminer;
    private final FileOperator fileOperator;

    public FormGetRoute(PathExaminer pathExaminer, FileOperator fileOperator) {
        super.routeString = "/form";
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());

        if (!pathExaminer.pathExists(fullPath)) {
            fileOperator.createFileAtPath(fullPath);
        }

        return new OkResponse(fileOperator.readContents(fullPath));
    }
}
