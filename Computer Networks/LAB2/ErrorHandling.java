import java.util.Scanner;

public class ErrorHandling {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the message (e.g., your register number): ");
        String regno = sc.next();

        String[] reg_bin = new String[regno.length()];
        System.out.println("\nBinary conversion of each character:");
        for (int i = 0; i < regno.length(); i++) {
            reg_bin[i] = toBinary(regno.charAt(i));
            System.out.println(regno.charAt(i) + " -> " + reg_bin[i]);
        }

        String lrc = calcLrc(reg_bin);
        System.out.println("\nLRC (Longitudinal Redundancy Check): " + lrc);

        String vrc = calcVrc(reg_bin);
        System.out.println("VRC (Vertical Redundancy Check): " + vrc);

        sc.close();
    }

    public static String toBinary(char character) {
        switch (character) {
            case '0': return "0000";
            case '1': return "0001";
            case '2': return "0010";
            case '3': return "0011";
            case '4': return "0100";
            case '5': return "0101";
            case '6': return "0110";
            case '7': return "0111";
            case '8': return "1000";
            case '9': return "1001";
            case 'A': case 'a': return "1010";
            case 'B': case 'b': return "1011";
            case 'C': case 'c': return "1100";
            case 'D': case 'd': return "1101";
            case 'E': case 'e': return "1110";
            case 'F': case 'f': return "1111";
            default: return "0000"; 
        }
    }

    public static String calcLrc(String[] reg_bin) {
        int length = reg_bin[0].length();
        int[] lrc = new int[length];

        for (String bin : reg_bin) {
            for (int i = 0; i < length; i++) {
                lrc[i] ^= Character.getNumericValue(bin.charAt(i));
            }
        }

        StringBuilder lrcResult = new StringBuilder();
        for (int bit : lrc) {
            lrcResult.append(bit);
        }
        return lrcResult.toString();
    }

    public static String calcVrc(String[] reg_bin) {
        StringBuilder vrcResult = new StringBuilder();

        for (String bin : reg_bin) {
            int onesCount = 0;

            for (int i = 0; i < bin.length(); i++) {
                if (bin.charAt(i) == '1') {
                    onesCount++;
                }
            }

            vrcResult.append(onesCount % 2 == 0 ? '0' : '1');
        }
        return vrcResult.toString();
    }
}