package httpserver.file;

import java.nio.file.Path;

public class Html {
    public static String hrefString(Path root, Path fullFilePath) {
        Path diff = root.relativize(fullFilePath);
        if (new PathExaminer().isDir(fullFilePath)) {
            return diff.toString() + "/";
        }
        return diff.toString();
    }

    public static String linkString(Path root, Path fullFilePath) {
        String href = hrefString(root, fullFilePath);
        return "<div><a href=\"/" + href +
                "\">/" + href + "</a></div>";
    }
}
