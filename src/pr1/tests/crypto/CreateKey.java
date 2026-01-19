package pr1.tests.crypto;

import treptowkolleg.edu.text.IOApplication;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CreateKey extends IOApplication {

    public static void main(String[] args) {
        new CreateKey();
    }

    @Override
    public void run() throws Exception {
        SecureRandom random = new SecureRandom();
        KeyPair keyPair = new KeyPair(); // Annahme: liefert PEM-fähigen privaten Schlüssel
        String privateKeyPem = keyPair.getPrivateKeyAsPem();
        User user = new User("Ben", "bensPassword".toCharArray());

        byte[] keyBytes = user.getPasswordHash();
        byte[] salt = user.getSalt();

        // 3. IV für AES-GCM (12 Byte = 96 Bit – optimal für GCM)
        byte[] iv = new byte[12];
        random.nextBytes(iv);

        // 4. Privaten Schlüssel (PEM) verschlüsseln
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParams = new GCMParameterSpec(128, iv); // 128-Bit Tag
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), gcmParams);
        byte[] encryptedPem = cipher.doFinal(privateKeyPem.getBytes(UTF_8));

        // 5. IV + Chiffretext zusammenfügen (IV ist nicht geheim!)
        byte[] ivAndEncrypted = new byte[12 + encryptedPem.length];
        System.arraycopy(iv, 0, ivAndEncrypted, 0, 12);
        System.arraycopy(encryptedPem, 0, ivAndEncrypted, 12, encryptedPem.length);

        // 6. Base64-kodieren für String-Speicherung
        String saltB64 = Base64.getEncoder().encodeToString(salt);
        String hashB64 = Base64.getEncoder().encodeToString(keyBytes);
        String encryptedKeyB64 = Base64.getEncoder().encodeToString(ivAndEncrypted);

        // 7. userRecord befüllen
        String[] userRecord = new String[3];
        userRecord[0] = user.getUsername();
        userRecord[1] = saltB64 + ":" + hashB64;
        userRecord[2] = encryptedKeyB64;

        createFileWriter("user.txt");
        printfToFile("%s %s %s%n", userRecord[0], userRecord[1], userRecord[2]);

        createFileWriter("public_key.pem");
        printfToFile("%s", keyPair.getPublicKeyAsPem());

        // Optional: Ausgabe zur Kontrolle
        println("✅ Benutzerdaten erstellt:");
        println("  Username: " + userRecord[0]);
        println("  Salt:Hash: " + userRecord[1]);
        println("  Verschlüsselter Private Key (gekürzt): " + userRecord[2].substring(0, Math.min(60, userRecord[2].length())) + "...");
    }
}
