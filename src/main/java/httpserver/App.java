package httpserver;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];
        SocketServer socketServer = new SocketServer(port, fileDirectory);
        try {
            socketServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
