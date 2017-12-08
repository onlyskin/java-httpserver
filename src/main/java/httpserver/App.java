package httpserver;

import httpserver.file.PathExaminer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    private static ExecutorService threadPool;
    private static ServerFactory serverFactory;
    private static SocketHandlerFactory socketHandlerFactory;

    public static void main(String[] args) throws IOException {
        threadPool = Executors.newFixedThreadPool(16);
        serverFactory = new ServerFactory(new PathExaminer(), new ServerSocketFactory());
        socketHandlerFactory = new SocketHandlerFactory();

        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];

        Server server = serverFactory.makeServer(port, fileDirectory);
        server.start(threadPool, socketHandlerFactory);
    }
}
