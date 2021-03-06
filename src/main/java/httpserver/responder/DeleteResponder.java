package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.NotFoundResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class DeleteResponder extends MethodResponder {
    private final FileOperator fileOperator;
    private final PathExaminer pathExaminer;

    public DeleteResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        super.method = Method.DELETE;
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());
        if (pathExaminer.pathExists(fullPath)) {
            fileOperator.deleteFileAtPath(fullPath);
            return new OkResponse("".getBytes());
        }

        return new NotFoundResponse();
    }

    public boolean allows(Request request) {
        return request.getPathString().equals("/form");
    }
}
