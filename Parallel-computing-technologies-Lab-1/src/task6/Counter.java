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

    public synchronized void syncIncrement() {
        value++;
    }

    public synchronized void syncDecrement() {
        value--;
    }

    public void decrementWithBlock() {
        synchronized (this) {
            value--;
        }
    }
    public void incrementWithBlock() {
        synchronized (this) {
            value++;
        }
    }

    private final Object lock = new Object();

    public void incrementWithObjectLock() {
        lockValue(() -> {
            value++;
        });
    }

    public void decrementWithObjectLock() {
        lockValue(() -> {
            value--;
        });
    }

    private void lockValue(Runnable action) {
        synchronized (lock) {
            action.run();
        }
    }

    public int getValue() {
        return value;
    }
}
