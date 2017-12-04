package httpserver;

import java.io.*;

public class Responder {
    private final String fileDirectory;

    public Responder(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public Response makeResponse(Request request) {
        File file = new File(fileDirectory + request.getPath());
        byte[] payload;
        int status_code;

        if (file.isFile()) {
            payload = readFileContents(file);
            status_code = 200;
        }
        else if (file.isDirectory()) {
            status_code = 200;
            payload = getDirectoryListingBytes(file);
        }
        else {
            payload = "".getBytes();
            status_code = 404;
        }

        return new Response(status_code, payload);
    }

    private byte[] getDirectoryListingBytes(File directory) {
        String output = "";
        String[] files = directory.list();
        for (String f : files) {
            output = output + f + "\r\n";
        }
        return output.getBytes();
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
