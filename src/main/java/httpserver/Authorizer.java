package httpserver;

import java.util.Base64;

public class Authorizer {
    public boolean authorize(Request request) {
        if (request.hasHeader("Authorization")) {
            String[] credentials = decodeBasicAuthHeader(request.getHeaderValue("Authorization"));
            if (validateCredentials(credentials)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    private boolean validateCredentials(String[] credentials) {
        return credentials[0].equals("admin") && credentials[1].equals("hunter2");
    }

    public String[] decodeBasicAuthHeader(String authHeaderValue) {
        String[] parts = authHeaderValue.split(" ", 2);
        String decoded = new String(Base64.getDecoder().decode(parts[1]));
        return decoded.split(":", 2);
    }
}
