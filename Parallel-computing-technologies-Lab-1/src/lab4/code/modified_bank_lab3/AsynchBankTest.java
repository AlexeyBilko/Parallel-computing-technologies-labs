package lab4.code.modified_bank_lab3;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

public class AsynchBankTest {
    public static final int NACCOUNTS = 500;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);

        //ForkJoin
        /*ArrayList<TransferModified> transferActions = new ArrayList<>();
        for (int i = 0; i < NACCOUNTS; i++) {
            transferActions.add(new TransferModified(bank, i, INITIAL_BALANCE, 100000));
        }
        try {
            long start = System.currentTimeMillis();
            ForkJoinTask.invokeAll(transferActions);
            long finish = System.currentTimeMillis();
            System.out.println("ForkJoin: " + (finish - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        //
        ArrayList<TransferThread> transferThreads = new ArrayList<>();
        for (int i = 0; i < NACCOUNTS; i++) {
            transferThreads.add(new TransferThread(bank, i, INITIAL_BALANCE));
        }
        try {
            long start = System.currentTimeMillis();
            for (var thread : transferThreads) {
                thread.start();
            }
            for (var thread : transferThreads) {
                thread.join();
            }
            long finish = System.currentTimeMillis();
            System.out.println("ForkJoin: " + (finish - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
