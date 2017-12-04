package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private final String fileDirectory;
    private final int port;

    public SocketServer(int port, String fileDirectory) {
        this.port = port;
        this.fileDirectory = fileDirectory;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        IoProcessor ioProcessor = new IoProcessor(port, fileDirectory);
        ioProcessor.process(clientSocket.getInputStream(), clientSocket.getOutputStream());
    }
}
