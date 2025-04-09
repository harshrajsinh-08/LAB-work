package LABFAT;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Base64;

public class RSAExample {

    // Method to generate RSA key pair
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // You can use 1024 or 4096 too
        return generator.generateKeyPair();
    }

    // Encrypt message using RSA public key
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = encryptCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt message using RSA private key
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(encryptedText);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(decryptCipher.doFinal(bytes));
    }

    public static void main(String[] args) {
        try {
            // 1. Generate key pair
            KeyPair keyPair = generateKeyPair();

            String originalMessage = "Hello RSA! This is Harsh.";
            System.out.println("Original Message: " + originalMessage);

            // 2. Encrypt the message
            String encryptedMessage = encrypt(originalMessage, keyPair.getPublic());
            System.out.println("Encrypted Message: " + encryptedMessage);

            // 3. Decrypt the message
            String decryptedMessage = decrypt(encryptedMessage, keyPair.getPrivate());
            System.out.println("Decrypted Message: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}