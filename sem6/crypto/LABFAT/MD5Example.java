package LABFAT;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Example {
    public static void main(String[] args) {
        String input = "Hello, world!";
        String md5Hash = getMD5(input);
        
        if (md5Hash != null) {
            System.out.println("Original String: " + input);
            System.out.println("MD5 Hash: " + md5Hash);
        } else {
            System.out.println("Error generating hash.");
        }
    }

    public static String getMD5(String input) {
        try {
            // Create MD5 MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Calculate the digest (returns bytes)
            byte[] digest = md.digest(input.getBytes());

            // Convert byte array into hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));  // format as 2-digit hex
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}