package Entities;

import CharacterLogic.Movement.Movement;
import Sprites.Positioning;
import Sprites.ProcessedSpriteSheet;
import Sprites.SpriteProcessor;
import Utility.Logging;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Sprites.Positioning.*;

public class PlaceholderPlayer {

    private static final Logging LOG = new Logging(PlaceholderPlayer.class);
    private static final int SCALE = 64;

    private String path;

    private SpriteProcessor spriteProcessor;
    private ProcessedSpriteSheet spriteSheet;

    private boolean forward = false;
    private boolean backward = false;
    private boolean left = false;
    private boolean right = false;

    private double interFrameDelta;
    private static final double targetGameFramerate = 120; //TODO - take from game settings so it will scale with different refresh rates
    private static final double animationFramerate = 12; //TODO - load with image settings so more can be abstracted - allow it to change? Speed up and slow down game speed via delta or internal logic?
    private static final double maxInterFrameDelta = targetGameFramerate / animationFramerate;

    //Player stats
    private double maxVelocity = 3D;
    private double accelerationBase = 0.2D;
    private double decelerationBase = 0.5D;

    //Movement
    private Positioning position;
    private Movement movement;
//    private boolean movingForward;
//    private boolean movingBackward;
//    private boolean movingLeft;
//    private boolean movingRight;
//
//    private boolean running;
//    private boolean crouching;

    public PlaceholderPlayer(String path, int cordX, int cordY) {
        this.path = path;
        this.interFrameDelta = 0;

        movement = new Movement(accelerationBase, decelerationBase, maxVelocity, cordX, cordY);

        loadSprite();
        loadPosition();
    }

    public void loadSprite() {
        spriteSheet = new ProcessedSpriteSheet<Positioning>();
        spriteProcessor = new SpriteProcessor(path, SCALE);
        spriteProcessor.crop(0, 0, spriteSheet, Positioning.FACING_FORWARD);
        spriteProcessor.crop(0, 1, spriteSheet, Positioning.FACING_LEFT);
        spriteProcessor.crop(0, 2, spriteSheet, Positioning.FACING_BACKWARD);
        spriteProcessor.crop(0, 3, spriteSheet, Positioning.FACING_RIGHT);

        for (int i = 0; i < 9; i++) {
            spriteProcessor.crop(i, 0, spriteSheet, WALKING_FORWARD);
        }
        for (int i = 0; i < 9; i++) {
            spriteProcessor.crop(i, 1, spriteSheet, WALKING_LEFT);
        }
        for (int i = 0; i < 9; i++) {
            spriteProcessor.crop(i, 2, spriteSheet, WALKING_BACKWARD);
        }
        for (int i = 0; i < 9; i++) {
            spriteProcessor.crop(i, 3, spriteSheet, WALKING_RIGHT);
        }
    }

    public void loadPosition() {
        position = Positioning.FACING_RIGHT;
//        movingForward = true;
//        movingBackward = false;
//        movingLeft = false;
//        movingRight = false;
//
//        running = false;
//        crouching = false;
    }

    public BufferedImage getSprite() {
        return spriteSheet.getImage(position, movement.getSafeFrame(backward, forward, left, right));
    }

    public BufferedImage getUpdatedSprite() {
        try {
            spriteSheet.getImage(position, movement.getNextFrame(position, forward, backward, left, right));
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println(forward + " - " + backward + " - " + left + " - " + right);
            LOG.printWithLevel(1, "MOVING=" + (forward == false && backward == false && left == false && right == false));
            LOG.printWithLevel(1, "SIZE=" + spriteSheet.getArray(position).size() + " | GETTING->" + movement.getNextFrame(position, forward, backward, left, right));
            LOG.printWithLevel(1, position + " - " + movement.getNextFrame(position, forward, backward, left, right));
        }
        return spriteSheet.getImage(position, movement.getNextFrame(position, forward, backward, left, right));
    }

    private void determinePosition() {
        position = movement.determinePosition(position);
    }

    public void draw(Graphics g, double delta) {
        determinePosition();
        if (requiresNewFrame(delta)) {
            g.drawImage(getUpdatedSprite(), movement.getCordX(), movement.getCordY(), SCALE, SCALE, null);
        } else {
            g.drawImage(getSprite(), movement.getCordX(), movement.getCordY(), SCALE, SCALE, null);
        }
    }

    public void move(double delta) {
        movement.move(delta, forward, backward, left, right);
    }

    private boolean requiresNewFrame(double delta) {
        boolean requiresNewFrame = false;
        this.interFrameDelta += delta;
        if (interFrameDelta > maxInterFrameDelta) {
            interFrameDelta -= maxInterFrameDelta;
//            return true;
            requiresNewFrame = true;
        }
//        return false;
        return requiresNewFrame;
    }

    // ====================  SETTERS & GETTERS  ====================

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setPosition(Positioning position) {
        this.position = position;
    }

    public double getxVelocity() {
        return movement.getxVelocity();
    }

    public double getyVelocity() {
        return movement.getyVelocity();
    }

    public int getX() {
        return movement.getCordX();
    }

    public int getY() {
        return movement.getCordY();
    }
}
