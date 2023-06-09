package task5;

public class Main {
    public static void main(String[] args) {

        Console console = new Console();

        for (int i = 0; i < 100; i++) {
            OutputThread thread1 = new OutputThread(50, '|', false, console);
            OutputThread thread2 = new OutputThread(50, '-', true, console);

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
        }
    }
}