package task2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 700;
    public static BallsInHolesCounter CatchedBallsCounter;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("task2 programm");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        JPanel counterPanel = new JPanel();
        JLabel counterLabel = new JLabel("Balls in holes: 0");
        CatchedBallsCounter = new BallsInHolesCounter(counterLabel);
        counterPanel.add(counterLabel);
        counterPanel.setBackground(Color.lightGray);
        content.add(counterPanel, BorderLayout.NORTH);
        content.add(this.canvas, BorderLayout.CENTER);
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonAddHole = new JButton("Add Hole");
        JButton buttonStart = new JButton("Add ball");
        JButton buttonStart100 = new JButton("Add 100 balls");
        JButton buttonStart1000 = new JButton("Add 1000 balls");
        JButton buttonStart5000 = new JButton("Add 5000 balls");
        JButton buttonStart10000 = new JButton("Add 10000 balls");
        JButton buttonStop = new JButton("Stop");
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas);
                canvas.add(b);

                BallThread thread = new BallThread(b,CatchedBallsCounter);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        buttonAddHole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Hole b = new Hole(canvas);
                canvas.addHole(b);
                canvas.repaint();
            }
        });
buttonStart100.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 100; i++){
            Ball b = new Ball(canvas);
            canvas.add(b);

            BallThread thread = new BallThread(b, CatchedBallsCounter);
            thread.start();
        }
    }
});
        buttonStart1000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1000; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b, CatchedBallsCounter);
                    thread.start();
                }
            }
        });
        buttonStart5000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 5000; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b, CatchedBallsCounter);
                    thread.start();
                }
            }
        });
        buttonStart10000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10000; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b, CatchedBallsCounter);
                    thread.start();
                }
            }
        });
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonAddHole);
        buttonPanel.add(buttonStart100);
        buttonPanel.add(buttonStart1000);
        buttonPanel.add(buttonStart5000);
        buttonPanel.add(buttonStart10000);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}