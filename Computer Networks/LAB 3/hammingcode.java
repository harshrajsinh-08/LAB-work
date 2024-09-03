public class hammingcode {

    public static String toBinary(int number) {
        return String.format("%08d", Integer.parseInt(Integer.toBinaryString(number)));
    }

    public static String calculateHamming15_11(String data) {
        if (data.length() != 11) {
            throw new IllegalArgumentException("Data should be exactly 11 bits.");
        }

        char[] hammingCode = new char[15];
        hammingCode[2] = data.charAt(0); // d1
        hammingCode[4] = data.charAt(1); // d2
        hammingCode[5] = data.charAt(2); // d3
        hammingCode[6] = data.charAt(3); // d4
        hammingCode[8] = data.charAt(4); // d5
        hammingCode[9] = data.charAt(5); // d6
        hammingCode[10] = data.charAt(6); // d7
        hammingCode[11] = data.charAt(7); // d8
        hammingCode[12] = data.charAt(8); // d9
        hammingCode[13] = data.charAt(9); // d10
        hammingCode[14] = data.charAt(10); // d11

        hammingCode[0] = (char) ((hammingCode[2] ^ hammingCode[4] ^ hammingCode[6] ^ hammingCode[8] ^ hammingCode[10] ^ hammingCode[12] ^ hammingCode[14]) + '0'); // p1
        hammingCode[1] = (char) ((hammingCode[2] ^ hammingCode[5] ^ hammingCode[6] ^ hammingCode[9] ^ hammingCode[10] ^ hammingCode[13] ^ hammingCode[14]) + '0'); // p2
        hammingCode[3] = (char) ((hammingCode[4] ^ hammingCode[5] ^ hammingCode[6] ^ hammingCode[11] ^ hammingCode[12] ^ hammingCode[13]) + '0'); // p4
        hammingCode[7] = (char) ((hammingCode[8] ^ hammingCode[9] ^ hammingCode[10] ^ hammingCode[11] ^ hammingCode[14]) + '0'); // p8

        return new String(hammingCode);
    }

    public static void main(String[] args) {
        int number = 238;

        String binaryData = toBinary(number);
        String paddedData = String.format("%11s", binaryData).replace(' ', '0'); // Pad binary data to 11 bits
        System.out.println("Padded binary representation: " + paddedData);

        String hammingCode = calculateHamming15_11(paddedData);
        System.out.println("Hamming(15,11) code: " + hammingCode);
    }
}