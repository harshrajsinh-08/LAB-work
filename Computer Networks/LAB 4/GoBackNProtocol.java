import java.util.Random;
import java.util.Scanner;

public class GoBackNProtocol {
    private int winSize = 7;
    private int frameId = 0;
    private Random random = new Random();

    public void sendFr(int totFrames) {
        while (frameId < totFrames) {
            int windowEnd = Math.min(frameId + winSize, totFrames);
            for (int i = frameId; i < windowEnd; i++) {
                System.out.println("Sender: Sending Frame " + i);
                int scene = random.nextInt(3);
                if (scene == 1) {
                    System.out.println("Error: Frame " + i + " lost during transmission.");
                    System.out.println("Sender: Go-Back-N triggered. Resending frames from Frame " + frameId);
                    break;
                } else if (scene == 2) {
                    System.out.println("Receiver: Frame " + i + " received.");
                    System.out.println("Error: Acknowledgment for Frame " + i + " lost.");
                    System.out.println("Sender: Go-Back-N triggered. Resending frames from Frame " + frameId);
                    break;
                } else {
                    System.out.println("Receiver: Frame " + i + " received successfully.");
                    System.out.println("Receiver: Sending acknowledgment for Frame " + i);
                    frameId++;
                }
            }
            System.out.println("--------------------------------------------");
        }
        System.out.println("All frames have been sent successfully!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the total number of frames to send: ");
        int totFrames = sc.nextInt();
        GoBackNProtocol gbn = new GoBackNProtocol();
        gbn.sendFr(totFrames);
    }
}