package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.MethodNotAllowedResponse;
import httpserver.response.NotFoundResponse;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class DeleteResponder implements Responder {
    private final FileOperator fileOperator;
    private final PathExaminer pathExaminer;

    public DeleteResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        if (!handles(request.getPathString())) {
            return new MethodNotAllowedResponse();
        }

        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());
        if (pathExaminer.pathExists(fullPath)) {
            fileOperator.deleteFileAtPath(fullPath);
            return new OkResponse("".getBytes());
        }

        return new NotFoundResponse();
    }

    public boolean handles(String pathString) {
        return pathString.equals("/form");
    }
}
