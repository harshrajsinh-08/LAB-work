import java.security.*;
import java.util.Base64;

public class trydss {
    public static void main(String[] args)throws Exception{
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
        keygen.initialize(1024);
        KeyPair kp = keygen.generateKeyPair();
        PrivateKey pr = kp.getPrivate();
        PublicKey pu = kp.getPublic();

        String pri = Base64.getEncoder().encodeToString(pr.getEncoded());
        String pub = Base64.getEncoder().encodeToString(pu.getEncoded());
        System.out.println("Private Key: " + pri);
        System.out.println("Public Key: " + pub);

        String message = "This is a secret message.";
        byte[] messageBytes = message.getBytes();

        Signature sign = Signature.getInstance("SHA1withDSA");
        sign.initSign(pr);
        sign.update(messageBytes);
        byte[] sigbyt = sign.sign();

        String sig = Base64.getEncoder().encodeToString(sigbyt);
        System.out.println("Message: " + message);
        System.out.println("Signature: " + sig);

        Signature verify = Signature.getInstance("SHA1withDSA");
        verify.initVerify(pu);
        verify.update(messageBytes);
        boolean isVerified = verify.verify(sigbyt);
        System.out.println("Signature Verified? " + isVerified);
        
    } 
}
