package task6;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);

        /*Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.NonSyncIncrement();
            }
        });

        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.NonSyncDecrement();
            }
        });*/
        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        });

        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.decrement();
            }
        });

        /*Thread decrementBlockThread = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                counter.decrementWithBlock();
            }
        });

        Thread decrementObjectThread = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                counter.decrementWithObjectLock();
            }
        });*/

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        /*incrementThread.start();
        decrementBlockThread.start();
        decrementObjectThread.start();

        incrementThread.join();
        decrementBlockThread.join();
        decrementObjectThread.join();*/

        System.out.println("Final counter value: " + counter.getValue());
    }
}
