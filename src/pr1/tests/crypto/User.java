package pr1.tests.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public final class User {
    private final String username;
    private final byte[] salt;
    private final byte[] passwordHash;

    public User(String username, char[] password) throws Exception {
        this.username = username;
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, this.salt);
    }

    public User(String username, String hash) {
        if (username == null || hash == null) {
            throw new IllegalArgumentException("Username und Salt:Hash dürfen nicht null sein.");
        }
        String[] parts = hash.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Ungültiges Salt:Hash-Format. Erwartet: 'salt:hash'");
        }
        try {
            this.username = username;
            this.salt = Base64.getDecoder().decode(parts[0]);
            this.passwordHash = Base64.getDecoder().decode(parts[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ungültiges Base64-Format in Salt oder Hash.", e);
        }
    }

    public User(String username, byte[] salt, byte[] passwordHash) {
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
    }

    public boolean verifyPassword(char[] password) throws Exception {
        byte[] inputHash = hashPassword(password, this.salt);
        return Arrays.equals(this.passwordHash, inputHash);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private byte[] hashPassword(char[] password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 600_000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }
}
