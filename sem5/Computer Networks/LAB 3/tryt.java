import java.util.Scanner;

public class tryt {
   public static void hmm(String data){
    char[] hm = new char[22];
    StringBuilder sb = new StringBuilder(data);
    sb.reverse();
    data=sb.toString();

    int dataindex=0;
   for(int i = 0;i<22;i++)
    {
        if (i == 1 || i == 2 || i == 4 || i == 8 || i == 16) {
            hm[i] = 'p';
        }else{
            if(dataindex<data.length()){
                hm[i]=data.charAt(dataindex);
                dataindex++;
            }
        }
    }
    for(int i=21;i>0;i--){
        System.out.print(hm[i] + " ");
    }
    hm[1] = (char) (((hm[3] ^ hm[5] ^ hm[7] ^ hm[9] ^ hm[11] ^ hm[13] ^ hm[15] ^ hm[17] ^ hm[19] ^ hm[21]) & 1)+ '0');

    hm[2] = (char) (((hm[3] ^ hm[6] ^ hm[7] ^ hm[10] ^ hm[11] ^ hm[14] ^ hm[15] ^ hm[18] ^ hm[19] ^ hm[21]) & 1)+ '0');

    hm[4] = (char) (((hm[5] ^ hm[6] ^ hm[7] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1) + '0');

    hm[8] = (char) (((hm[9] ^ hm[10] ^ hm[11] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1) + '0');

    hm[16] = (char) (((hm[17] ^ hm[18] ^ hm[19] ^ hm[20] ^ hm[21]) & 1) + '0');

    System.out.print("Hamming array: ");
        for (int i = 21; i >= 0; i--) {
            System.out.print(hm[i] + "");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        String binaryData = "0010001000111000";
        System.out.println("Sender side: ");
        hmm(binaryData);
    }
}