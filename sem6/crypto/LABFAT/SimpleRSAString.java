package LABFAT;
public class SimpleRSAString {
    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    static int modPow(int base, int exp, int mod) {
        int result = 1;
        while (exp > 0) {
            if (exp % 2 == 1) {  
                result = (result * base) % mod;
            }
            base = (base * base) % mod;  
            exp = exp / 2;  
        }
        return result;
    }

    static int modInverse(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((d * e) % phi == 1) return d;
        }
        return -1;
    }

    public static void main(String[] args) {
        // 1. Key generation with small primes
        int p = 13, q = 17;
        int n = p * q;            // n = 119
        int phi = (p - 1) * (q - 1); // phi = 96

        int e = 5; // Public exponent
        while (gcd(e, phi) != 1) e++;

        int d = modInverse(e, phi); // Private exponent

        System.out.println("Public Key: (" + e + ", " + n + ")");
        System.out.println("Private Key: (" + d + ", " + n + ")");

        // 2. Input string
        String message = "Hi! My name is Harshraj";
        System.out.println("Original Message: " + message);

        // 3. Encrypt the message (char-by-char)
        int[] encrypted = new int[message.length()];
        System.out.print("Encrypted: ");
        for (int i = 0; i < message.length(); i++) {
            int m = (int) message.charAt(i);
            encrypted[i] = modPow(m, e, n);
            System.out.print(encrypted[i] + " ");
        }

        // 4. Decrypt the message
        StringBuilder decrypted = new StringBuilder();
        for (int val : encrypted) {
            int dec = modPow(val, d, n);
            decrypted.append((char) dec);
        }

        System.out.println("\nDecrypted Message: " + decrypted.toString());
    }
}