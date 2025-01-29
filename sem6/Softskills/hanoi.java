import java.util.*;

public class hanoi {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.print("Enter the number of disks-");
        int n = s1.nextInt();
        towerofHanoi(n, 'A', 'B', 'C');
    }

    public static void towerofHanoi(int n, char fromrod, char helperrod, char torod) {
        if (n == 1) {
            System.out.println("Move disk from " + fromrod + "->" + torod);
            return;
        }
        towerofHanoi(n - 1, fromrod, torod, helperrod);
        towerofHanoi(1, fromrod, helperrod, torod);
        towerofHanoi(n - 1, helperrod, fromrod, torod);
    }
}
