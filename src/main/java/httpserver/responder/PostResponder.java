package httpserver.responder;

import httpserver.*;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class PostResponder implements Responder {
    private final PathExaminer pathExaminer;
    private final FileOperator fileOperator;

    public PostResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        if (allowed(request.getPathString())) {
            Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());
            if (pathExaminer.pathExists(fullPath)) {
                fileOperator.replaceContents(fullPath, request.getBody().getBytes());
                return new OkResponse(fileOperator.readContents(fullPath));
            } else {
                fileOperator.createFileAtPath(fullPath);
                fileOperator.replaceContents(fullPath, request.getBody().getBytes());
                return new OkResponse(fileOperator.readContents(fullPath));
            }
        }
        return new MethodNotAllowedResponse();
    }

    private boolean allowed(String pathString) {
        return pathString.equals("/form");
    }
}
