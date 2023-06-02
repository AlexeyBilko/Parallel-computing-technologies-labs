package lab3.task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int arrSize;

    public Producer(Drop drop, int maxSize) {
        this.drop = drop;
        this.arrSize = maxSize;
    }

    public void run() {
        Random random = new Random();
        for (int i = 0; i < arrSize; i++) {
            int num = random.nextInt(100);
            drop.put(num);
            System.out.printf("PRODUCER: %d;\n", num);
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }
}
