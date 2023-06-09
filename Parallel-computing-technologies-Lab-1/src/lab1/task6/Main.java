package task6;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);

        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.NonSyncIncrement();
                //counter.syncIncrement();
                //counter.incrementWithBlock();
                //counter.incrementWithObjectLock();
            }
        });

        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter.NonSyncDecrement();
                //counter.syncDecrement();
                //counter.decrementWithBlock();
                //counter.decrementWithObjectLock();

            }
        });

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();


        System.out.println("Final counter value: " + counter.getValue());
    }
}
