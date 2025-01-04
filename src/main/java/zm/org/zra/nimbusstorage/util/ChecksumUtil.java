package zm.org.zra.nimbusstorage.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ChecksumUtil {

    @Value("${nimbus-storage.checksum-algorithm}")
    private String algorithm;


    public  String calculateChecksum(InputStream fileInputStream)  {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] buffer = new byte[1024];
        int bytesRead;

        while (true) {
            try {
                if ((bytesRead = fileInputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            digest.update(buffer, 0, bytesRead);
        }

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest.digest()) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
