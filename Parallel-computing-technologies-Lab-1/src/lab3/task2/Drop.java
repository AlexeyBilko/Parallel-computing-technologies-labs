package lab3.task2;

import java.util.Arrays;

public class Drop {
    private int[] numbers;
    private int nextIndex = 0;
    private boolean empty = true;

    public Drop(int size) {
        numbers = new int[size];
    }

    public synchronized void put(int number) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        numbers[nextIndex] = number;
        nextIndex++;
        empty = false;
        notifyAll();
    }

    public synchronized int take() {
/*        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }*/
        if(!empty) {
            int value = numbers[nextIndex-1];
            nextIndex--;
            empty = (nextIndex == 0);
            return value;
            //notifyAll();
        }
        return 0;
    }
}
