package LABFAT;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class aes {

    // Encrypts plain text using AES
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // AES with padding
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypts base64 string using AES
    public static String decrypt(String cipherText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Generate AES key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // You can also use 192 or 256 if supported
            SecretKey secretKey = keyGen.generateKey();

            // Input
            System.out.print("Enter plaintext: ");
            String plainText = scanner.nextLine();

            // Encrypt
            String encrypted = encrypt(plainText, secretKey);
            System.out.println("Encrypted text: " + encrypted);

            // Decrypt
            String decrypted = decrypt(encrypted, secretKey);
            System.out.println("Decrypted text: " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}