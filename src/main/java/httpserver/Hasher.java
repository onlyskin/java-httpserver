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
            System.out.println("Unable to get hash.");
            return "";
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hash) {
            stringBuffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }
}
