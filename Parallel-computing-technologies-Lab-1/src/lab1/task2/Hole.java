package task2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Hole {
    private Component canvas;
    private static final int Radius = 20;
    private int x = 0;
    private int y = 0;

    public int GetRadius()
    {
        return Radius;
    }
    public int GetPosition_X()
    {
        return this.x;
    }
    public int GetPosition_Y()
    {
        return this.y;
    }

    public Hole(Component c) {
        this.canvas = c;


        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = new Random().nextInt(this.canvas.getHeight());
        } else {
            x = new Random().nextInt(this.canvas.getWidth());
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public static void f() {
        int a = 0;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fill(new Ellipse2D.Double(x, y, Radius, Radius));

    }

}