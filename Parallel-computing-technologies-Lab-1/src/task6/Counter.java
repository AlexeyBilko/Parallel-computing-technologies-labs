package task6;

public class Counter {
    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public void NonSyncIncrement() {
        value++;
    }
    public void NonSyncDecrement() {
        value--;
    }

    // Method-level synchronization using synchronized keyword
    public synchronized void increment() {
        value++;
    }

    public synchronized void decrement() {
        value--;
    }

    // Block-level synchronization using synchronized keyword and this object lock
    public void decrementWithBlock() {
        synchronized (this) {
            value--;
        }
    }

    // Block-level synchronization using synchronized keyword and a separate lock object
    private final Object lockObject = new Object();
    public void decrementWithObjectLock() {
        synchronized (lockObject) {
            value--;
        }
    }

    public int getValue() {
        return value;
    }
}
