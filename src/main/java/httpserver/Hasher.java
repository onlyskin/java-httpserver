package httpserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public boolean matches(byte[] input, String hash) {
        return getHash(input).equals(hash);
    }

    public String getHash(byte[] input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            byte[] hash = messageDigest.digest(input);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
