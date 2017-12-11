package httpserver;

import java.nio.file.Path;

public class AppConfig {
    private final Logger logger;
    private final Path root;

    public AppConfig(Logger logger, Path root) {
        this.logger = logger;
        this.root = root;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getRoot() {
        return root;
    }
}
