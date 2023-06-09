package task5;

public class OutputThread extends Thread {
    private final int count;
    private final char symbol;
    private final boolean expectedFlagValue;
    private final Console console;

    public OutputThread(int count, char symbol, boolean expectedFlagValue, Console console) {
        this.console = console;
        this.count = count;
        this.symbol = symbol;
        this.expectedFlagValue = expectedFlagValue;
    }

    @Override
    public synchronized void run() {
            for (int i = 0; i < count; i++) {
                console.PrintSymbol(symbol, expectedFlagValue);
                //console.SimplePrintSymbol(symbol);
            }
    }
}