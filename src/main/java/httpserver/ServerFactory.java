package httpserver;

import httpserver.file.PathExaminer;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerFactory {
    private ServerSocketFactory serverSocketFactory;
    private PathExaminer pathExaminer;

    public ServerFactory(PathExaminer pathExaminer, ServerSocketFactory serverSocketFactory) {
        this.serverSocketFactory = serverSocketFactory;
        this.pathExaminer = pathExaminer;
    }
    public Server makeServer(int port, String fileDirectory) throws IOException {
        Path root = pathExaminer.getPath(fileDirectory);
        Path logPath = Paths.get(root.toString(), "logs");
        ServerSocket serverSocket = serverSocketFactory.newServerSocket(port);

        return new Server(serverSocket, root, logPath);
    }
}
