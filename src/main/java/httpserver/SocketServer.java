package httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private final String fileDirectory;
    private final int port;
    private final ServerSocketFactory serverSocketFactory;

    public SocketServer(int port, String fileDirectory, ServerSocketFactory serverSocketFactory) {
        this.port = port;
        this.fileDirectory = fileDirectory;
        this.serverSocketFactory = serverSocketFactory;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = serverSocketFactory.makeServerSocket(port);
        serverSocket.accept();
    }
}
