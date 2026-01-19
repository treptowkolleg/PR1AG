package pr1.tests.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyEncoder {

    public static String toPem(Key key, String type) {
        String encodedKey = Base64.getMimeEncoder(64, "\n".getBytes())
                .encodeToString(key.getEncoded());
        return "-----BEGIN " + type + " KEY-----\n" +
                encodedKey +
                "\n-----END " + type + " KEY-----\n";
    }

    public static Key toKey(String pem) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        String publicKeyPEM = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}
