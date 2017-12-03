package httpserver;

import java.io.*;

public class Responder {
    public Response makeResponse(Request request) {
        File file = new File(request.getPath());
        byte[] payload;
        int status_code;

        if (file.isFile()) {
            payload = readFileContents(file);
            status_code = 200;
        }
        else {
            payload = "".getBytes();
            status_code = 404;
        }

        return new Response(status_code, payload);
    }

    private byte[] readFileContents(File file) {
        byte[] payload = new byte[(int) file.length()];

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(payload);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return payload;
    }
}
