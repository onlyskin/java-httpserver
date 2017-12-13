package httpserver;

import httpserver.responder.GeneralResponder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHandlerFactory {
    public SocketHandler newSocketHandler(AppConfig appConfig, Socket clientSocket) throws IOException {
        return new SocketHandler(appConfig,
                clientSocket.getInputStream(),
                new RequestParser(appConfig),
                new GeneralResponder(new ResponderSupplierFactory().makeResponderSupplier()),
                new ResponseWriter(clientSocket.getOutputStream()));
    }

    public SocketHandler newSocketHandlerFromStreams(AppConfig appConfig, InputStream inputStream, OutputStream outputStream) throws IOException {
        return new SocketHandler(appConfig,
                inputStream,
                new RequestParser(appConfig),
                new GeneralResponder(new ResponderSupplierFactory().makeResponderSupplier()),
                new ResponseWriter(outputStream));
    }
}
