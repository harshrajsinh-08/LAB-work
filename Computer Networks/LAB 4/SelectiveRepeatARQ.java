import java.util.Random;
import java.util.Scanner;

public class SelectiveRepeatARQ {
    private int totalFr;
    private int winSize = 7;
    private boolean[] frames;
    private int winStart = 0;
    private Random random = new Random();

    public SelectiveRepeatARQ(int totalFr) {
        this.totalFr = totalFr;
        this.frames = new boolean[totalFr];
    }

    public void sendFrames() {
        while (winStart < totalFr) {
            for (int i = winStart; i < Math.min(winStart + winSize, totalFr); i++) {
                if (!frames[i]) {
                    System.out.println("Sending Frame " + i);
                    int scene = random.nextInt(3);
                    if (scene == 0) {
                        System.out.println("Frame " + i + " received.");
                        frames[i] = true;
                        System.out.println("Acknowledgment for Frame " + i + " sent.");
                    } else if (scene == 1) {
                        System.out.println("Error: Frame " + i + " lost.");
                    } else {
                        System.out.println("Frame " + i + " received.");
                        System.out.println("Error: Acknowledgment for Frame " + i + " lost.");
                    }
                }
            }
            while (winStart < totalFr && frames[winStart]) {
                winStart++;
            }
            System.out.println("Sliding window to Frame " + winStart);
            System.out.println("--------------------------------------");
        }
        System.out.println("All frames sent successfully!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the total number of frames: ");
        int totalFr = sc.nextInt();
        SelectiveRepeatARQ arq = new SelectiveRepeatARQ(totalFr);
        arq.sendFrames();
    }
}