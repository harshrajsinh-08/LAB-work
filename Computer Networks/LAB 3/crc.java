import java.util.Scanner;

public class crc {

    public static String binary(String input) {
        StringBuilder binstr = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(input.charAt(i)));
            String binary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(digit)));
            binstr.append(binary);
        }
        
        return binstr.toString();
    }

    public static String cal_crc(String input, String polynomial) {
        int dataLen = input.length();
        int polyLen = polynomial.length();
        StringBuilder data = new StringBuilder(input);
        for (int i = 0; i < polyLen - 1; i++) {
            data.append("0");
        }
        for (int i = 0; i <= dataLen; i++) {
            if (data.charAt(i) == '1') {
                for (int j = 0; j < polyLen; j++) {
                    char newChar = (char) (data.charAt(i + j) ^ polynomial.charAt(j) ^ '0');
                    data.setCharAt(i + j, newChar);
                }
            }
        }
        return data.substring(dataLen);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the integer string: ");
        String input = sc.nextLine();
        String binstr = binary(input);
        System.out.println("Binary representation: " + binstr);
        String polynomial = "101";
        String crc = cal_crc(binstr, polynomial);
        System.out.println("The CRC value (remainder) is: " + crc);
    }
}