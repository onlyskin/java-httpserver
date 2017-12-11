package httpserver;

import java.nio.file.Path;

public class AppConfig {
    private final Logger logger;
    private final Path root;

    public AppConfig(Path root, Logger logger) {
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
