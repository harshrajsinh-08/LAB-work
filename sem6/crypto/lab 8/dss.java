import java.math.BigInteger;
import java.util.Random;

public class dss {
    private static final int BIT_LENGTH = 8;
    private static final Random random = new Random();

    public static void main(String[] args) {
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, random);
        BigInteger p = genPrime(q);
        BigInteger g = findGenerator(p, q);
        BigInteger x = new BigInteger(BIT_LENGTH, random).mod(q).add(BigInteger.ONE);
        BigInteger y = g.modPow(x, p);

        System.out.println("Public Key (p, q, g, y): \n" + p + "\n" + q + "\n" + g + "\n" + y);
        System.out.println("Private Key (x): " + x);

        String message = "Hello My name is Harshraj.. I am a Secret agent of India";
        BigInteger[] signature = signMessage(message, p, q, g, x);

        System.out.println("Signature (r, s): " + signature[0] + ", " + signature[1]);

        boolean isValid = verifySig(message, signature, p, q, g, y);
        System.out.println("Signature Verification: " + (isValid ? "Valid" : "Invalid"));
    }

    private static BigInteger genPrime(BigInteger q) {
        BigInteger k;
        BigInteger p;
        do {
            k = new BigInteger(BIT_LENGTH, random);
            p = q.multiply(k).add(BigInteger.ONE);
        } while (!p.isProbablePrime(10) || !p.subtract(BigInteger.ONE).mod(q).equals(BigInteger.ZERO));
        return p;
    }

    private static BigInteger findGenerator(BigInteger p, BigInteger q) {
        BigInteger g;
        do {
            g = new BigInteger(p.bitLength(), random).mod(p);
        } while (g.compareTo(BigInteger.TWO) < 0 || !g.modPow(q, p).equals(BigInteger.ONE));
        return g;
    }

    private static BigInteger simpleHash(String message, BigInteger q) {
        BigInteger hash = BigInteger.ZERO;
        for (char c : message.toCharArray()) {
            hash = hash.add(BigInteger.valueOf((int) c));
        }
        return hash.mod(q);
    }

    private static BigInteger[] signMessage(String message, BigInteger p, BigInteger q, BigInteger g, BigInteger x) {
        BigInteger k, r, s;
        do {
            k = new BigInteger(BIT_LENGTH, random).mod(q).add(BigInteger.ONE);
            r = g.modPow(k, p).mod(q);
            s = (simpleHash(message, q).add(x.multiply(r))).multiply(k.modInverse(q)).mod(q);
        } while (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO));
        return new BigInteger[]{r, s};
    }

    private static boolean verifySig(String message, BigInteger[] signature, BigInteger p, BigInteger q, BigInteger g, BigInteger y) {
        BigInteger r = signature[0], s = signature[1];
        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0 || s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0) {
            return false;
        }
        BigInteger w = s.modInverse(q);
        BigInteger u1 = simpleHash(message, q).multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
        return v.equals(r);
    }
}
