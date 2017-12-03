package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port, String fileDirectory) throws IOException {
        this.port = port;

        this.serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        serverSocket.accept();
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
