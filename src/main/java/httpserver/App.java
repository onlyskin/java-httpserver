package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];

        PathExaminer pathExaminer = new PathExaminer();
        Path root = pathExaminer.getPath(fileDirectory);
        Path logPath = pathExaminer.concatenate(root,"logs");
        AppConfig appConfig = new AppConfig(root, new Logger(logPath, new FileOperator(), System.out));

        ServerFactory serverFactory = new ServerFactory(new ServerSocketFactory(), appConfig);
        SocketHandlerFactory socketHandlerFactory = new SocketHandlerFactory();

        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        Server server = serverFactory.makeServer(port);
        while (true) {
            server.acceptConnection(threadPool, socketHandlerFactory);
        }
    }
}
