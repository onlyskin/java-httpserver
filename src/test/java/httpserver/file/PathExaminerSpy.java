package httpserver.file;

import java.nio.file.Path;

public class PathExaminerSpy extends PathExaminer {
    public String getPathCalledWith;
    private Path getPathResult;
    public Path concatenateCalledWith1;
    public String concatenateCalledWith2;

    public PathExaminerSpy(Path getPathResult) {
        this.getPathCalledWith = null;
        this.getPathResult = getPathResult;
    }

    @Override
    public Path getPath(String input) {
        this.getPathCalledWith = input;
        return getPathResult;
    }

    @Override
    public Path concatenate(Path root, String suffix) {
        this.concatenateCalledWith1 = root;
        this.concatenateCalledWith2 = suffix;
        return null;
    }
}
