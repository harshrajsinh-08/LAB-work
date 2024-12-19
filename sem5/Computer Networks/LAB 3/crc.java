import java.util.Scanner;

public class crc {
    public static String cal_crc(String input, String polynomial) {
        int dataLen = input.length();
        int polyLen = polynomial.length();
        StringBuilder data = new StringBuilder(input);
        for (int i = 0; i < polyLen - 1; i++) {
            data.append("0");
        }
        for (int i = 0; i <= dataLen-1; i++) {
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
        String polynomial = "101";
        String crc = cal_crc(input, polynomial);
        System.out.println("The CRC value (remainder) is: " + crc);
    }
}