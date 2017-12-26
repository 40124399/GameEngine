package GUI;

import javax.swing.*;
import java.awt.*;

public class Game extends Canvas implements Runnable {

    private static final long ONE_BIL = 1000000000;
    private static final int TARGET_FPS = 115;
    private static final double OPTIMAL_TIME = (double)ONE_BIL / (double)TARGET_FPS;

    //From Shit Video
    private static final int WIDTH = 160;
    private static final int HEIGHT = WIDTH / 12 * 9;
    private static final int SCALE = 3;
    private static final String NAME = "GAME";

    private JFrame jFrame;
    private boolean running = false;

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0L;
        int fps = 0;

        while(running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps ++;

            if(lastFpsTime >= ONE_BIL) {
                System.out.println("(FPS: "+fps+" , TIME: "+lastFpsTime+")");
                lastFpsTime = 0;
                fps = 0;
            }

            doGameUpdates(delta);
            render();

            try {
                Thread.sleep((long) (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch(Exception e) {
                System.out.println("FAIL");
            }
        }
    }

    private void doGameUpdates(double delta) {

    }

    private void render() {

    }

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        jFrame = new JFrame(NAME);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        jFrame.add(this, BorderLayout.CENTER);
        jFrame.pack();

        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }
}
