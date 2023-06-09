package task2;

public class BallThread extends Thread {
    private Ball b;
    private ScoredBalls coughtBalls;

    public BallThread(Ball ball, ScoredBalls coughtBalls){
        b = ball;
        this.coughtBalls = coughtBalls;
    }

    @Override
    public void run() {
        try {
            while (!b.InHole())
            {
                b.move();
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
            coughtBalls.increment();
            b.deleteBall();
        }
        catch(InterruptedException ex)
        {

        }
    }
}

