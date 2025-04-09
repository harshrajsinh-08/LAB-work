package LABFAT;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

public class sha256 {

    /*
     * without key ::
     * 
     * import java.security.MessageDigest;
     * import java.util.Scanner;
     * 
     * public class SHA256Example {
     * public static String getSHA256(String input) throws Exception {
     * MessageDigest digest = MessageDigest.getInstance("SHA-256");
     * byte[] hash = digest.digest(input.getBytes());
     * return bytesToHex(hash);
     * }
     * 
     * public static String bytesToHex(byte[] bytes) {
     * StringBuilder hex = new StringBuilder();
     * for (byte b : bytes) {
     * hex.append(String.format("%02x", b));
     * }
     * return hex.toString();
     * }
     * 
     * public static void main(String[] args) throws Exception {
     * Scanner scanner = new Scanner(System.in);
     * System.out.print("Enter text to hash: ");
     * String text = scanner.nextLine();
     * 
     * String hash = getSHA256(text);
     * System.out.println("SHA-256 Hash: " + hash);
     * }
     * }
     * 
     * 
     */

    public static String computeMAC(byte[] data, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] macBytes = mac.doFinal(data);
        return bytesToHex(macBytes);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your secret key: ");
        String secretKeyInput = scanner.nextLine();
        byte[] secretKey = secretKeyInput.getBytes();
        System.out.print("Enter your data: ");
        String data = scanner.nextLine();
        byte[] dataBytes = data.getBytes();
        long start = System.nanoTime();
        String mac = computeMAC(dataBytes, secretKey);
        long end = System.nanoTime();
        System.out.println("MAC: " + mac);
        System.out.printf("Time taken: %.3f ms\n\n", (end - start) / 1_000_000.0);
    }
}