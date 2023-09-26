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
            int count = 0;
            try {
                for (char c : forA.take().toCharArray()) {
                    if (c == 'a') count++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Буква 'a' встретилась: " + count);
        }).start();

        new Thread(() -> {
            int count = 0;
            try {
                for (char c : forB.take().toCharArray()) {
                    if (c == 'b') count++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Буква 'b' встретилась: " + count);
        }).start();

        new Thread(() -> {
            int count = 0;
            try {
                for (char c : forC.take().toCharArray()) {
                    if (c == 'c') count++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Буква 'c' встретилась: " + count);
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
}
