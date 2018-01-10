package httpserver.responder;

import httpserver.*;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.request.Request;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class PostResponder extends MethodResponder {
    private final PathExaminer pathExaminer;
    private final FileOperator fileOperator;

    public PostResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        super.method= Method.POST;
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
            fileOperator.createFileAtPath(fullPath);
        }

        fileOperator.replaceContents(fullPath, request.getBody().getBytes());
        return new OkResponse(fileOperator.readContents(fullPath));
    }

    public boolean allows(String pathString) {
        return pathString.equals("/form") || pathString.equals("/method_options");
    }
}
