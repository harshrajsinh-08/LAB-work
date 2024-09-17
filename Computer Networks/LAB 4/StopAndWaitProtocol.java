import java.util.Random;
import java.util.Scanner;

public class StopAndWaitProtocol {
    
    private int frId;
    private boolean isAck;
    private Random random;

    public StopAndWaitProtocol() {
        this.frId = 0;
        this.isAck = true;
        this.random = new Random();
    }

    public void sendFr() {
        System.out.println("================================================");
        System.out.println("Sending Frame " + frId);
        isAck = false;
        
        int scene = random.nextInt(3);
        
        switch (scene) {
            case 0:
                System.out.println("Frame " + frId + " received.");
                sendAck();
                break;
            case 1:
                System.out.println("Error: Frame " + frId + " lost.");
                break;
            case 2:
                System.out.println("Frame " + frId + " received.");
                System.out.println("Error: Acknowledgment lost.");
                break;
        }
        
        if (!isAck) {
            System.out.println("Timeout! Resending Frame " + frId);
            sendFr();
        }
        System.out.println("-----------------------------------------------");
    }

    private void sendAck() {
        System.out.println("Acknowledgment for Frame " + frId + " sent.");
        isAck = true;
        frId++;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of frames to send: ");
        int totalFrames = sc.nextInt();

        StopAndWaitProtocol swp = new StopAndWaitProtocol();
        
        for (int i = 0; i < totalFrames; i++) {
            swp.sendFr();
        }

        System.out.println("All frames sent successfully!");
    }
}