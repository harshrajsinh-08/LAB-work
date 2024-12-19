import java.util.Scanner;

public class CaesarCipher {
    public static String enc(String text, int key) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (Character.isUpperCase(c)) {
                char enchar = (char) (((c - 'A' + key) % 26) + 'A');
                res.append(enchar);
            } else if (Character.isLowerCase(c)) {
                char enchar = (char) (((c - 'a' + key) % 26) + 'a');
                res.append(enchar);
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    public static String decrypt(String text, int key) {
        return enc(text, 26 - (key % 26));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the text: ");
        String text = sc.nextLine();
        System.out.println("Enter the key: ");
        int key = sc.nextInt();

        String encrypt = enc(text, key);
        System.out.println("encrypted Text: " + encrypt);

        String decrypt = decrypt(encrypt, key);
        System.out.println("Decrypted Text: " + decrypt);
    }
}