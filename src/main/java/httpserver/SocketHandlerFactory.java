package httpserver;

import httpserver.request.RequestParser;
import httpserver.responder.Responder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHandlerFactory {
    public SocketHandler newSocketHandler(AppConfig appConfig, Socket clientSocket) throws IOException {
        return new SocketHandler(appConfig,
                clientSocket.getInputStream(),
                new RequestParser(appConfig),
                new Responder(new MethodResponderSupplierFactory().makeResponderSupplier()),
                new ResponseWriter(clientSocket.getOutputStream(), appConfig.getLogger()));
    }

    public SocketHandler newSocketHandlerFromStreams(AppConfig appConfig, InputStream inputStream, OutputStream outputStream) throws IOException {
        return new SocketHandler(appConfig,
                inputStream,
                new RequestParser(appConfig),
                new Responder(new MethodResponderSupplierFactory().makeResponderSupplier()),
                new ResponseWriter(outputStream, appConfig.getLogger()));
    }
}
