import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> forA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> forB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> forC = new ArrayBlockingQueue<>(100);
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                try {
                    forA.put(generateText("abc", 100_000));
                    forB.put(generateText("abc", 100_000));
                    forC.put(generateText("abc", 100_000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            queueAnaliz(forA, 'a');
        }).start();

        new Thread(() -> {
            queueAnaliz(forB, 'b');
        }).start();

        new Thread(() -> {
            queueAnaliz(forC, 'c');
        }).start();
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void queueAnaliz(BlockingQueue<String> queue, char ch) {
        int count = 0;
        try {
            for (char c : queue.take().toCharArray()) {
                if (c == ch) count++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Буква " + ch + " встретилась: " + count);
    }
}
