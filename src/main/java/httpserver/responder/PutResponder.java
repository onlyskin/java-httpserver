package httpserver.responder;

import httpserver.AppConfig;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.NotFoundResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class PutResponder implements Responder {
    private final PathExaminer pathExaminer;
    private final FileOperator fileOperator;

    public PutResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        if (!allows(request.getPathString())) {
            return new MethodNotAllowedResponse();
        }

        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());

        if (!pathExaminer.pathExists(fullPath)) {
            return new NotFoundResponse();
        }

        fileOperator.replaceContents(fullPath, request.getBody().getBytes());
        return new OkResponse(fileOperator.readContents(fullPath));
    }

    public boolean allows(String pathString) {
        return pathString.equals("/form") || pathString.equals("/method_options");
    }
}
