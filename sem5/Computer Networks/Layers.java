import java.util.Scanner;

public class Layers {

    public static void main(String[] args) {
        Scanner osi = new Scanner(System.in);
        System.out.print("Enter your message: ");
        String msg = osi.nextLine();

        String[] hdrs = {
            "AH","PH","SH","TH","NH","DH"};
        String trls = "DT";

        System.out.println("\nTRANSMITTER:");
        for (String head : hdrs) {
            msg = head +" "+msg;
            System.out.println(msg);
        }
        msg = msg+" " + trls;
        System.out.println(msg);

        System.out.println("msg entered into physical layer and transmitted.\n");

        System.out.println("RECEIVER:");
        System.out.println("msg received at physical layer");

        msg = msg.substring(0, msg.length() - trls.length());
        System.out.println(msg);
        for (int i = hdrs.length - 1; i >= 0; i--) {
            msg = msg.replaceFirst(hdrs[i], "");
            System.out.println(msg);
        }
    }
}