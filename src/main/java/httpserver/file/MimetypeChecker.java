package httpserver.file;

import javax.activation.MimetypesFileTypeMap;
import java.nio.file.Path;

public class MimetypeChecker {

    public String getMimeType(Path path) {
        String filename = path.toString();
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("image/png png");
        return mimetypesFileTypeMap.getContentType(filename);
    }
}
