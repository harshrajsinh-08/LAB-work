import java.security.*;
import java.util.Base64;

public class dsslib {
    public static void main(String[] args) throws Exception {
        // 1. Generate DSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024);  // DSS typically uses 1024-bit keys
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String priv = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Private Key: " + priv);
        System.out.println();
        System.out.println("Public Key: " + pub);
        System.out.println();

        // 2. Message to sign
        String message = "This is a secret message.";
        byte[] messageBytes = message.getBytes();

        // 3. Sign the message using the private key
        Signature signer = Signature.getInstance("SHA1withDSA");
        signer.initSign(privateKey);
        signer.update(messageBytes);
        byte[] signatureBytes = signer.sign();

        // 4. Display the signature (Base64 encoded)
        String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
        System.out.println("Message: " + message);
        System.out.println("Signature: " + signatureBase64);

        // 5. Verify the signature using the public key
        Signature verifier = Signature.getInstance("SHA1withDSA");
        verifier.initVerify(publicKey);
        verifier.update(messageBytes);
        boolean isVerified = verifier.verify(signatureBytes);

        System.out.println("Signature Verified? " + isVerified);
    }
}