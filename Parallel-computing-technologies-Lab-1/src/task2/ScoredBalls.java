package task2;

import javax.swing.*;

public class ScoredBalls {
    public volatile int count;
    public final JLabel counterLabel;

    public ScoredBalls(JLabel ctrLabel){
        count = 0;
        counterLabel = ctrLabel;
    }
    public synchronized void increment(){
        count++;
        counterLabel.setText(this.toString());
    }

    @Override
    public String toString() {
        return "Balls scored: " + count;
    }
}
