package pr1.tests.crypto;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public final class KeyPair {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public KeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(3072);
        publicKey = kpg.generateKeyPair().getPublic();
        privateKey = kpg.generateKeyPair().getPrivate();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyAsPem() {
        return KeyEncoder.toPem(publicKey, "PUBLIC");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getPrivateKeyAsPem() {
        return KeyEncoder.toPem(privateKey, "PRIVATE");
    }
}
