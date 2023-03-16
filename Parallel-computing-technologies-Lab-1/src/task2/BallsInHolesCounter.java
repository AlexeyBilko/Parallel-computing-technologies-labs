package task2;

import javax.swing.*;

public class BallsInHolesCounter {
    public volatile int value;
    public final JLabel counterLabel;
    public BallsInHolesCounter(JLabel ctrLabel){
        value = 0;
        counterLabel = ctrLabel;
    }
    public synchronized void increment(){
        value++;
        counterLabel.setText(this.toString());
    }

    @Override
    public String toString() {
        return "Balls caught: " + value;
    }
}
