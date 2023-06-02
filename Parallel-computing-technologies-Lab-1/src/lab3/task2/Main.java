package lab3.task2;

public class Main {
    public static void main(String[] args) {
        int arrSize = 100;
        Drop drop = new Drop(arrSize);
        (new Thread(new Producer(drop, arrSize))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
