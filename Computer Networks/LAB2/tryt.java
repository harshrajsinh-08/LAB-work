import java.util.Scanner;
public class tryt{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String regno = sc.nextLine();
        String[] regbin = new String[regno.length()];
        for(int i=0;i<regno.length();i++){
            regbin[i]= toBinary(regno.charAt(i));
            System.out.println(regno.charAt(i)+" -> "+regbin[i]);
        }
        calc(regbin);
        sc.close();
    }
    public static String toBinary(char ch){
        int value=0;
        if(Character.isDigit(ch)){
            value = ch-'0';
        }else{
            value = Character.toUpperCase(ch)-'A'+10;
        }
        String binary = Integer.toBinaryString(value);
        while(binary.length()<4){
            binary = "0"+binary;
        }
        return binary;
    }
    public static void calc(String[] regbin){
        int lrc[] = new int[4];
        StringBuilder lrcResult = new StringBuilder();
        StringBuilder vrcResult = new StringBuilder();
        for(String s: regbin){
            int ones=0;
            for(int i=0;i<4;i++){
                lrc[i]^= Character.getNumericValue(s.charAt(i));
                if(s.charAt(i)=='1') ones++;
            }
            vrcResult.append((ones%2==0)?'0':'1');
        }
        for(int i:lrc){
            lrcResult.append(i);
        }
        System.out.println("LRC : "+lrcResult.toString());
        System.out.println("VRC :"+ vrcResult.toString());
    }
}