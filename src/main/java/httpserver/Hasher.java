package httpserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public String getHash(byte[] input) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(input);
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : hash) {
            stringBuffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }
}
