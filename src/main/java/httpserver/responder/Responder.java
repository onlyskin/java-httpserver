package httpserver.responder;

import httpserver.Request;
import httpserver.Response;

import java.nio.file.Path;

public interface Responder {
    Response respond(Path root, Request request);
}
