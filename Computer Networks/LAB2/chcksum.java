import java.util.Scanner;
public class chcksum {
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the transmission message (e.g., register number): ");
        String regno = sc.nextLine();

        System.out.println("\nBinary conversion of each character:");
        for (int i = 0; i < regno.length(); i++) {
            char ch = regno.charAt(i);
            String binary = charbin(ch);
            System.out.print(ch + " -> " + binary + "   ");
        }
        System.out.println();
        String checksum = chcksumfxn(regno);
        System.out.println("\nCalculated Checksum at Transmitter: " + checksum);
    }

    public static String charbin(char ch) {
        int value;
        if (Character.isDigit(ch)) {
            value = ch - '0';
        } else {
            value = Character.toUpperCase(ch) - 'A' + 10;
        }
        String binary = Integer.toBinaryString(value);
        while (binary.length() < 4) {
            binary = "0" + binary;
        }
        return binary;
    }

    public static String chcksumfxn(String regno) {
        int sum = 0;
        System.out.println("\nCalculating Checksum:");
        for (int i = 0; i < regno.length(); i++) {
            char ch = regno.charAt(i);
            String binary = charbin(ch);
            int temp = Integer.parseInt(binary, 2);
            sum += temp;
            System.out.println("Processing digit: " + ch + "  => Current sum: " + Integer.toBinaryString(sum) + " (" + sum + ")");
        }
        System.out.println();

        String binsum = Integer.toBinaryString(sum);
        while (binsum.length() > 4) {
            int carry = Integer.parseInt(binsum.substring(0, binsum.length() - 4), 2);
            int wrappedSum = Integer.parseInt(binsum.substring(binsum.length() - 4), 2) + carry;
            binsum = Integer.toBinaryString(wrappedSum);
            System.out.println("Sum after wrapping: " + binsum);
        }

        String checksum = "";
        for (char bit : binsum.toCharArray()) {
            checksum += (bit == '0') ? '1' : '0';
        }
        while (checksum.length() < 4) {
            checksum = "1" + checksum;
        }
        System.out.println();
        System.out.println("Final Checksum: " + checksum);
        return checksum;
    }
}