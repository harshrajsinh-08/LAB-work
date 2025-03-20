import java.util.*;
public class MD5HashGenerator {
    private static final int[] SHIFT_AMOUNTS = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };
    
    private static final int[] SINE_TABLE = new int[64];
    
    static {
        for (int i = 0; i < 64; i++) {
            SINE_TABLE[i] = (int) (Math.floor(Math.abs(Math.sin(i + 1)) * Math.pow(2, 32)));
        }
    }
    
    private static final int INIT_A = 0x67452301;
    private static final int INIT_B = 0xEFCDAB89;
    private static final int INIT_C = 0x98BADCFE;
    private static final int INIT_D = 0x10325476;
    
    public String generateMD5Hash(String message) {
        byte[] messageBytes = message.getBytes();
        int originalBitLength = messageBytes.length * 8;
        int paddedLength = ((((originalBitLength + 64) / 512) + 1) * 512) / 8;
        byte[] paddedMessage = new byte[paddedLength];
        System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
        paddedMessage[messageBytes.length] = (byte) 0x80;
        long messageLength = originalBitLength & 0xFFFFFFFFL;
        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedLength - 8 + i] = (byte) ((messageLength >>> (i * 8)) & 0xFF);
        }
        int a = INIT_A;
        int b = INIT_B;
        int c = INIT_C;
        int d = INIT_D;
        for (int chunkOffset = 0; chunkOffset < paddedLength; chunkOffset += 64) {
            int[] chunk = new int[16];
            for (int i = 0; i < 16; i++) {
                chunk[i] = bytesToInt(paddedMessage, chunkOffset + (i * 4));
            }
            int originalA = a;
            int originalB = b;
            int originalC = c;
            int originalD = d;
            for (int i = 0; i < 64; i++) {
                int f = 0;
                int g = 0;
                if (i < 16) {
                    f = (b & c) | ((~b) & d);
                    g = i;
                } else if (i < 32) {
                    f = (d & b) | ((~d) & c);
                    g = (5 * i + 1) % 16;
                } else if (i < 48) {
                    f = b ^ c ^ d;
                    g = (3 * i + 5) % 16;
                } else {
                    f = c ^ (b | (~d));
                    g = (7 * i) % 16;
                }
                int temp = d;
                d = c;
                c = b;
                b = b + leftRotate((a + f + SINE_TABLE[i] + chunk[g]), SHIFT_AMOUNTS[i]);
                a = temp;
            }
            a += originalA;
            b += originalB;
            c += originalC;
            d += originalD;
        }
        
        byte[] md5Hash = new byte[16];
        intToBytes(a, md5Hash, 0);
        intToBytes(b, md5Hash, 4);
        intToBytes(c, md5Hash, 8);
        intToBytes(d, md5Hash, 12);
        
        return bytesToHex(md5Hash);
    }
    
    private int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF)) |
               ((bytes[offset + 1] & 0xFF) << 8) |
               ((bytes[offset + 2] & 0xFF) << 16) |
               ((bytes[offset + 3] & 0xFF) << 24);
    }
    
    private void intToBytes(int value, byte[] bytes, int offset) {
        bytes[offset] = (byte)(value & 0xFF);
        bytes[offset + 1] = (byte)((value >>> 8) & 0xFF);
        bytes[offset + 2] = (byte)((value >>> 16) & 0xFF);
        bytes[offset + 3] = (byte)((value >>> 24) & 0xFF);
    }
    
    private int leftRotate(int value, int shift) {
        return ((value << shift) | (value >>> (32 - shift)));
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static void main(String[] args) {
        MD5HashGenerator generator = new MD5HashGenerator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your string: ");
        String inputString = sc.nextLine();
        String hash = generator.generateMD5Hash(inputString);
        System.out.println("MD5(\"" + inputString + "\") = " + hash);
    }
}
