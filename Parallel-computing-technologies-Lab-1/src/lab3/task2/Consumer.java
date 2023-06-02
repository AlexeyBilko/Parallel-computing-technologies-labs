package lab3.task2;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        int[] data = new int[1];
        int counter = 0;
        for (int message = drop.take(); message != -1; message = drop.take()) {
            data[counter] = message;

            int[] tmp = data;
            data = new int[data.length + 1];
            for (int i = 0; i < tmp.length; i++){
                data[i] = tmp[i];
            }
            counter = tmp.length;

            System.out.printf("CONSUMER GOT: %d;\n", data[counter-1]);
            System.out.printf("CONSUMER ARRAY (LENGHT: %d)\n", data.length);
            for (int i = 0; i < data.length; i++){
                System.out.printf("%d;",data[i]);
            }
            System.out.printf("\n");
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {}
        }
    }
}
