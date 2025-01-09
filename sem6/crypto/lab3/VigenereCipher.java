import java.util.Scanner;

public class VigenereCipher {

    public static String encrypt(String plaintext, String key) {
        StringBuilder cipher = new StringBuilder();
        plaintext = plaintext.toUpperCase();
        key = generateKey(plaintext, key).toUpperCase();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            if (Character.isLetter(plainChar)) {
                char encryptedChar = (char) (((plainChar + keyChar - 2 * 'A') % 26) + 'A');
                cipher.append(encryptedChar);
            } else {
                cipher.append(plainChar);
            }
        }

        return cipher.toString();
    }

    public static String decrypt(String cipher, String key) {
        StringBuilder plaintext = new StringBuilder();
        cipher = cipher.toUpperCase();
        key = generateKey(cipher, key).toUpperCase();

        for (int i = 0; i < cipher.length(); i++) {
            char ciphChar = cipher.charAt(i);
            char keyChar = key.charAt(i);

            if (Character.isLetter(ciphChar)) {
                char decryptedChar = (char) (((ciphChar - keyChar + 26) % 26) + 'A');
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(ciphChar);
            }
        }

        return plaintext.toString();
    }

    private static String generateKey(String text, String key) {
        StringBuilder expKey = new StringBuilder(key);
        while (expKey.length() < text.length()) {
            expKey.append(key);
        }
        return expKey.substring(0, text.length());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Vigenère Cipher Program ===");

        System.out.print("Enter the text to encrypt: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter the key for encryption: ");
        String key = scanner.nextLine();

        String encryptedText = encrypt(plaintext, key);
        System.out.println("\nEncrypted Text: " + encryptedText);

        System.out.print("\nEnter the text to decrypt: ");
        String cipherText = scanner.nextLine();

        System.out.print("Enter the key for decryption: ");
        String decryptionKey = scanner.nextLine();

        String decryptedText = decrypt(cipherText, decryptionKey);
        System.out.println("\nDecrypted Text: " + decryptedText);

        System.out.println("\n=== Program Completed ===");

        scanner.close();
    }
}
