package lab4.code.modified_bank_lab3;

import java.util.Arrays;

class Bank {
    public static final int NTEST = 10000;
    private final Account[] accounts;
    public Bank(int n, int initialBalance){
        accounts = new Account[n];
        Arrays.fill(accounts, new Account(initialBalance));
    }

    public void transfer(int from, int to, int amount) {
        synchronized (accounts[from]) {
            while (accounts[from].money < amount) {
                try {
                    accounts[from].wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            accounts[from].money -= amount;
        }
        synchronized (accounts[to]) {
            accounts[to].money += amount;
            accounts[to].notifyAll();
        }
    }
    public int size(){
        return accounts.length;
    }
}

class Account {
    public int money;

    public Account(int value) {
        this.money = value;
    }
}
