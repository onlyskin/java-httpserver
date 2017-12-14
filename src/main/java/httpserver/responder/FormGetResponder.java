package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.OkResponse;
import httpserver.response.Response;

import java.io.IOException;
import java.nio.file.Path;

public class FormGetResponder implements Responder {
    private final PathExaminer pathExaminer;
    private final FileOperator fileOperator;

    public FormGetResponder(PathExaminer pathExaminer, FileOperator fileOperator) {
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());
        if (pathExaminer.pathExists(fullPath)) {
            return new OkResponse(fileOperator.readContents(fullPath));
        } else {
            fileOperator.createFileAtPath(fullPath);
            return new OkResponse(fileOperator.readContents(fullPath));
        }
    }
}
