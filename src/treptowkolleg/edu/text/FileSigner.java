package treptowkolleg.edu.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class FileSigner {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }

    public static String signFile(Path file, PrivateKey privateKey) throws Exception {
        byte[] data = Files.readAllBytes(file);
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    public static void saveSignature(Path file, String signature) throws IOException {
        Path sigFile = Paths.get(file.toString() + ".sig");
        Files.write(sigFile, signature.getBytes());
    }
}
