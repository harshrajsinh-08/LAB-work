package LABFAT;
import java.util.*;
public class vig{
    public static String enc(String pt,String key){
        StringBuilder ct = new StringBuilder();
        StringBuilder newKey = new StringBuilder();
        for (int i = 0; i < pt.length(); i++) {
            newKey.append(key.charAt(i % key.length()));
        }
        key = newKey.toString();
        pt=pt.toLowerCase();
        key=key.toLowerCase();
        for(int i=0;i<pt.length();i++){
            char p = pt.charAt(i);
            char k = key.charAt(i);
            char c=(char)(((p-'a')+(k-'a'))%26+'a');
            ct.append(c);
        }
        return ct.toString();
    }
    public static String dec(String enc,String key){
        StringBuilder pt = new StringBuilder();
        StringBuilder newKey = new StringBuilder();
        for (int i = 0; i < enc.length(); i++) {
            newKey.append(key.charAt(i % key.length()));
        }
        enc=enc.toLowerCase();
        key=key.toLowerCase();
        for(int i=0;i<enc.length();i++){
            char c = enc.charAt(i);
            char k = key.charAt(i);
            char p = (char) (((c-'a') -(k-'a')+26) %26 + 'a');
            pt.append(p);
        }
        return pt.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plaintext:");
        String pt = sc.nextLine();
        System.out.println("Enter the key:");
        String key = sc.nextLine();
        StringBuilder k = new StringBuilder(key);
        while(k.length()<pt.length()){
            k.append(key);
        }
        key = k.toString();
        String ct = enc(pt,key);
        System.out.println("Ciphertext: " + ct);
        String decr = dec(ct,key);
        System.out.println("Decrypted text: " + decr);
    }
}