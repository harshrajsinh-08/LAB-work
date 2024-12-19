import java.util.*;

public class GoBackNARQ {
    private int windowSize = 7;
    private Random random = new Random();

    public void sendFrames(int totalFrames) {
        int base = 0;  

        while (base < totalFrames) {
            int windowEnd = Math.min(base + windowSize, totalFrames);
            for (int i = base; i < windowEnd; i++) {
                System.out.println("Sender: Sending Frame " + i);
                
                int scenario = random.nextInt(3);
                
                if (scenario == 0) {
                    System.out.println("Error: Frame " + i + " lost during transmission.");
                    System.out.println("Sender: Go-Back-N triggered. Resending frames from Frame " + base);
                    break;
                } else if (scenario == 1) {
                    System.out.println("Receiver: Frame " + i + " received.");
                    System.out.println("Error: Acknowledgment for Frame " + i + " lost.");
                    System.out.println("Sender: Go-Back-N triggered. Resending frames from Frame " + base);
                    break;
                } else {
                    System.out.println("Receiver: Frame " + i + " received successfully.");
                    System.out.println("Receiver: Sending acknowledgment for Frame " + i);
                    if (i == base) {
                        base++;
                    }
                }
            }
            System.out.println("--------------------------------------------");
        }
        System.out.println("All frames have been sent successfully!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the total number of frames to send: ");
        int totalFrames = sc.nextInt();
        GoBackNARQ protocol = new GoBackNARQ();
        protocol.sendFrames(totalFrames);
    }
}