package task3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.blue;
import static java.awt.Color.red;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 700;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("task3 program");
        this.canvas = new BallCanvas();
        System.out.println("Thread name in the frame  = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStop = new JButton("Stop");
        JButton buttonStart100 = new JButton("Add 100 blue balls");
        JButton buttonStart1000 = new JButton("Add 1000 blue balls");
        JButton buttonStart5000 = new JButton("Add 5000 blue balls");


        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonStart100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBalls(100);
            }
        });

        buttonStart1000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBalls(1000);
            }
        });
        buttonStart5000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBalls(5000);
            }
        });


        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonStart100);
        buttonPanel.add(buttonStart1000);
        buttonPanel.add(buttonStart5000);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    void AddBalls(int amount){
        for (int i = 0; i < amount; i++){
            Ball b = new Ball(canvas,blue);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
        Ball b = new Ball(canvas,red);
        canvas.add(b);
        BallThread thread = new BallThread(b);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}