import java.util.Random;
import java.util.Scanner;

public class selective{
    private int totalFrames;
    private int winSize = 7;
    private boolean[] frames;
    private int winStart = 0;
    private Random random = new Random();

    public selective(int totalFrames) {
        this.totalFrames = totalFrames;
        this.frames = new boolean[totalFrames];
    }
    public void sendFrames() {
        while (winStart < totalFrames) {
            for (int i = winStart; i < Math.min(winStart + winSize, totalFrames); i++) {
                if (!frames[i]) {
                    System.out.println("SENDING FRAME " + i);
                    int scenario = random.nextInt(3);
                    if (scenario == 0) {
                        System.out.println("FRAME " + i + " RECEIVED");
                        frames[i] = true;
                        System.out.println("ACK SENT FOR FRAME: " + i);
                    } else if (scenario == 1) {
                        System.out.println("ERROR: FRAME " + i + " LOST");
                    } else {
                        System.out.println("FRAME " + i + " RECEIVED");
                        System.out.println("ACK LOST FOR FRAME: " + i);
                    }
                }
            }

            while (winStart < totalFrames && frames[winStart]) {
                winStart++;
            }

            System.out.println("Sliding window to frame " + winStart);
            System.out.println("--------------------------------");
        }
        System.out.println("ALL FRAMES SENT SUCCESSFULLY");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter total number of frames:");
        int frames = sc.nextInt();
        selective p = new selective(frames);
        p.sendFrames();
    }
}