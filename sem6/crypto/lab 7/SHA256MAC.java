import java.util.Scanner;

public class SHA256MAC {
    private static final int[] CONSTANTS = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
        0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
        0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
        0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
        0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
        0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
        0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };
    private static final int[] INITIAL_HASH_VALUES = {
        0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };

    public String computeMAC(byte[] data, byte[] secretKey) {
        if (secretKey.length > 64) {
            secretKey = sha256(secretKey);
        }
        if (secretKey.length < 64) {
            byte[] paddedKey = new byte[64];
            System.arraycopy(secretKey, 0, paddedKey, 0, secretKey.length);
            secretKey = paddedKey;
        }
        byte[] innerPad = new byte[64];
        byte[] outerPad = new byte[64];
        
        for (int i = 0; i < 64; i++) {
            innerPad[i] = (byte) (secretKey[i] ^ 0x36);
            outerPad[i] = (byte) (secretKey[i] ^ 0x5c);
        }
        byte[] innerData = new byte[innerPad.length + data.length];
        System.arraycopy(innerPad, 0, innerData, 0, innerPad.length);
        System.arraycopy(data, 0, innerData, innerPad.length, data.length);
        byte[] innerHash = sha256(innerData);

        byte[] outerData = new byte[outerPad.length + innerHash.length];
        System.arraycopy(outerPad, 0, outerData, 0, outerPad.length);
        System.arraycopy(innerHash, 0, outerData, outerPad.length, innerHash.length);
        byte[] mac = sha256(outerData);
        
        return bytesToHex(mac);
    }
    private byte[] sha256(byte[] data) {
        byte[] paddedData = padData(data);
        int[] hashValues = INITIAL_HASH_VALUES.clone();
        
        for (int i = 0; i < paddedData.length; i += 64) {
            int[] messageSchedule = new int[64];
            for (int j = 0; j < 16; j++) {
                messageSchedule[j] = ((paddedData[i + j * 4] & 0xFF) << 24) |
                                     ((paddedData[i + j * 4 + 1] & 0xFF) << 16) |
                                     ((paddedData[i + j * 4 + 2] & 0xFF) << 8) |
                                     (paddedData[i + j * 4 + 3] & 0xFF);
            }
            for (int j = 16; j < 64; j++) {
                int s0 = rightRotate(messageSchedule[j - 15], 7) ^ rightRotate(messageSchedule[j - 15], 18) ^ (messageSchedule[j - 15] >>> 3);
                int s1 = rightRotate(messageSchedule[j - 2], 17) ^ rightRotate(messageSchedule[j - 2], 19) ^ (messageSchedule[j - 2] >>> 10);
                messageSchedule[j] = messageSchedule[j - 16] + s0 + messageSchedule[j - 7] + s1;
            }

            int a = hashValues[0];
            int b = hashValues[1];
            int c = hashValues[2];
            int d = hashValues[3];
            int e = hashValues[4];
            int f = hashValues[5];
            int g = hashValues[6];
            int h = hashValues[7];

            for (int j = 0; j < 64; j++) {
                int S1 = rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25);
                int ch = (e & f) ^ ((~e) & g);
                int temp1 = h + S1 + ch + CONSTANTS[j] + messageSchedule[j];
                int S0 = rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = S0 + maj;
                
                h = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }
            hashValues[0] += a;
            hashValues[1] += b;
            hashValues[2] += c;
            hashValues[3] += d;
            hashValues[4] += e;
            hashValues[5] += f;
            hashValues[6] += g;
            hashValues[7] += h;
        }
        byte[] hash = new byte[32];
        for (int i = 0; i < 8; i++) {
            hash[i * 4] = (byte) (hashValues[i] >>> 24);
            hash[i * 4 + 1] = (byte) (hashValues[i] >>> 16);
            hash[i * 4 + 2] = (byte) (hashValues[i] >>> 8);
            hash[i * 4 + 3] = (byte) hashValues[i];
        }
        
        return hash;
    }
    private byte[] padData(byte[] data) {
        int originalLength = data.length;
        long bitLength = originalLength * 8L;

        int paddingLength = (56 - (originalLength % 64));
        if (paddingLength <= 0) {
            paddingLength += 64;
        }
        byte[] paddedData = new byte[originalLength + paddingLength + 8];
        System.arraycopy(data, 0, paddedData, 0, originalLength);
        paddedData[originalLength] = (byte) 0x80;
        for (int i = 0; i < 8; i++) {
            paddedData[paddedData.length - 8 + i] = (byte) (bitLength >>> ((7 - i) * 8));
        }
        
        return paddedData;
    }
    private int rightRotate(int value, int shift) {
        return (value >>> shift) | (value << (32 - shift));
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
        SHA256MAC mac = new SHA256MAC();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your secret key: ");
        String secretKeyInput = sc.nextLine();
        byte[] secretKey = secretKeyInput.getBytes();
        int[] dataSizes = {100, 1024, 10240}; // 100 bytes, 1 KB, 10 KB
        
        for (int size : dataSizes) {
            byte[] data = new byte[size];
            for (int i = 0; i < size; i++) {
                data[i] = (byte) (i % 256);
            }
            long startTime = System.nanoTime();
            String result = mac.computeMAC(data, secretKey);
            long endTime = System.nanoTime();
            
            double elapsedTimeMs = (endTime - startTime) / 1_000_000.0;
            
            System.out.println("Data size: " + size + " bytes");
            System.out.println("MAC: " + result);
            System.out.println("Time taken: " + elapsedTimeMs + " ms");
            System.out.println();
        }
    }
}