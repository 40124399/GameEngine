package GUI;

import Entities.PlaceholderPlayer;
import Input.GameInputHandler;
import Input.PlaceholderInputHandler;
import Utility.Logging;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    //Frame Settings
    private JFrame jFrame;
    private static final int WIDTH = 160;
    private static final int HEIGHT = WIDTH / 12 * 9;
    private static final int SCALE = 5;
    private static final String NAME = "GAME";

    //Loop Settings
    private boolean running = false;
    private static final long ONE_BIL = 1000000000;
    private static final int TARGET_FPS = 110;
    private static final double OPTIMAL_TIME = (double)ONE_BIL / (double)TARGET_FPS;

    //Statistics / Debugging Settings
    private int avgFps = 0;
    private int tick = 0;
    private long avgFrameTime = 0L;
    private boolean drawFrameStats;
    private boolean testing;
    private static final Logging LOG = new Logging(Game.class);

    //Input
    private GameInputHandler inputHandler;

    //Placeholder
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private PlaceholderPlayer player = new PlaceholderPlayer("/PlayerSprites/placeholderPlayer.png");

    //Testing
    private String displayText = "____";

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        long frameTime = 0L;
        int fps = 0;

        while(running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            frameTime += updateLength;
            fps ++;

            tick();

            if(frameTime >= ONE_BIL) {
                if(drawFrameStats) {
                    avgFrameTime = frameTime;
                    avgFps = fps;
                    LOG.print("STATISTICS -> ","FPS: "+avgFps, "FRAME_TIME: "+avgFrameTime, "GAME_TICK: "+tick);
                }
                frameTime = 0;
                fps = 0;
            }

            doGameUpdates(delta);
            render();

            long sleepTime = (long) (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void tick() {
        tick++;

        if(inputHandler.up.isPressed())displayText = "UP";
        if(inputHandler.down.isPressed())displayText = "DOWN";
        if(inputHandler.left.isPressed())displayText = "LEFT";
        if(inputHandler.right.isPressed())displayText = "RIGHT";
    }

    private void doGameUpdates(double delta) {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = i + tick;
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        if(drawFrameStats) {
            g.setColor(Color.green);
            g.drawString("FPS: "+avgFps, 20, 20);
            g.drawString("FRAME_TIME: "+avgFrameTime, 20, 50);
            g.drawString("GAME_TICKS: "+tick, 20, 80);
        }

        if(testing) {
            g.setColor(Color.green);
            g.drawString(displayText, 20, 110);
        }

        g.dispose();
        bs.show();
    }

    public Game(boolean testing, boolean drawFrameStats) {
        this.testing = testing;
        this.drawFrameStats = drawFrameStats;
        this.inputHandler = new PlaceholderInputHandler(this);

        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        this.jFrame = new JFrame(NAME);
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
