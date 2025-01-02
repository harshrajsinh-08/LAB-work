import java.util.*;
public class Caesar_1and2{
    public static String enc(String text,int key){
        StringBuilder res = new StringBuilder();
        for(int i=0;i<text.length();i++){
            char c = text.charAt(i);
            if(Character.isUpperCase(c)){
                char temp  =(char)(((c-'A'+key)%26)+'A');
                res.append(temp);
            }
            else if(Character.isLowerCase(c)){
                char temp  =(char)(((c-'a'+key)%26)+'a');
                res.append(temp);
            }
            else if(Character.isDigit(c)){
                int temp = (c-'0'+key);
                int shift = (temp%26==0)?26:temp%26;
                res.append(shift);
            }
            else{
                res.append(c);
            }
        }
        return res.toString();
    }
    public static String decrypt(String enc,int key){
        String dec = enc(enc,26-(key%26));
        return dec;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the text: ");
        String text=sc.nextLine();
        System.out.println("Enter the key: ");
        int key = sc.nextInt();
        int finalkey = 0;
        String encrypt = enc(text,key);
        System.out.print("ENCRYPTED TEXT: "+ encrypt);
        System.out.println();
        System.out.println();
        String decrypt = decrypt(encrypt,key);
        System.out.print("DECRYPTED TEXT: "+ decrypt);
        System.out.println();
        String text2 = "vnnc vn jocna cqn cxpj yjach";
        System.out.println("trying to find the key for: "+text2);
        System.out.println();
        for(int i=1;i<26;i++){	 	  	 	  	   	      	  	  	    	       	 	
            key=i;
            String dec = decrypt(text2,key);
            System.out.print(i+ " "+ dec);
            System.out.println();
            if(dec.equals("meet me after the toga party")){
                finalkey = i;
            }
        }
        System.out.println("Key is: "+ finalkey);
    }
}