package Entities;

import Sprites.Positioning;
import Sprites.ProcessedSpriteSheet;
import Sprites.SpriteProcessor;
import Utility.Logging;
import javafx.geometry.Pos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.awt.*;

public class PlaceholderPlayer {

    private static final Logging LOG = new Logging(PlaceholderPlayer.class);
    private static final int SCALE = 64;

    private String path;

    private SpriteProcessor spriteProcessor;
    private ProcessedSpriteSheet spriteSheet;

    private int cordX;
    private int cordY;

    private boolean forward = false;
    private boolean backward = false;
    private boolean left = false;
    private boolean right = false;

    //Player stats
    private double maxVelocity = 3D;
    private double accelerationDelta = 0.2D;
    private double decelerationDelta = 0.5D;
    private double xVelocity = 0D;
    private double yVelocity = 0D;

    //Movement
    private Positioning position;
//    private boolean movingForward;
//    private boolean movingBackward;
//    private boolean movingLeft;
//    private boolean movingRight;
//
//    private boolean running;
//    private boolean crouching;

    public PlaceholderPlayer(String path, int cordX, int cordY) {
        this.path = path;
        this.cordX = cordX;
        this.cordY = cordY;

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

    public BufferedImage getSprite(Positioning position) {
        return spriteSheet.get(position);
    }

    public BufferedImage getSprite() {
        return spriteSheet.get(position);
    }

    private void determinePosition() {
        if (yVelocity < 0 && Math.abs(xVelocity) < Math.abs(yVelocity)) {
            setPosition(Positioning.FACING_FORWARD);
        }
        if (yVelocity > 0 && Math.abs(xVelocity) < Math.abs(yVelocity)) {
            setPosition(Positioning.FACING_BACKWARD);
        }
        if (xVelocity < 0 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
            setPosition(Positioning.FACING_LEFT);
        }
        if (xVelocity > 0 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
            setPosition(Positioning.FACING_RIGHT);
        }
    }

    public void draw(Graphics g) {
        determinePosition();
        g.drawImage(getSprite(), cordX, cordY, SCALE, SCALE, null);
    }

    public void move() {
        if (forward || backward) {
            if (forward) {
                yVelocity = lerpNeg(maxVelocity, yVelocity, accelerationDelta);
            }
            if (backward) {
                yVelocity = lerpPos(maxVelocity, yVelocity, accelerationDelta);
            }
        } else {
            yVelocity = lerpDecelerate(0, yVelocity, decelerationDelta);
        }
        if (left || right) {
            if (left) {
                xVelocity = lerpNeg(maxVelocity, xVelocity, accelerationDelta);
            }
            if (right) {
                xVelocity = lerpPos(maxVelocity, xVelocity, accelerationDelta);
            }
        } else {
            xVelocity = lerpDecelerate(0, xVelocity, decelerationDelta);
        }

        cordY = (int) (cordY + yVelocity);
        cordX = (int) (cordX + xVelocity);
    }

    private double lerpPos(double goalVelocity, double currentVelocity, double delta) {
        if ((currentVelocity + delta) > goalVelocity)
            return goalVelocity;
        return currentVelocity + delta;
    }

    private double lerpNeg(double goalVelocity, double currentVelocity, double delta) {
        if ((currentVelocity - delta) < -goalVelocity)
            return -goalVelocity;
        return currentVelocity - delta;
    }

    private double lerpDecelerate(double goalVelocity, double currentVelocity, double delta) {
        if (currentVelocity == goalVelocity)
            return goalVelocity;

        if (currentVelocity > 0) {
            if ((currentVelocity - delta) < goalVelocity)
                return goalVelocity;
            return currentVelocity - delta;
        }

        if (currentVelocity < 0) {
            if ((currentVelocity + delta) < goalVelocity)
                return goalVelocity;
            return currentVelocity + delta;
        }
        return goalVelocity;
    }

    //    /**
//     * OLD!!
//     * @param path
//     * @param i
//     */
//    public PlaceholderPlayer(String path, int i) {
//        this.path = path;
//        LOG.printWithLevel(10, "ATTEMPTING TO LOAD -> "+path);
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(PlaceholderPlayer.class.getResourceAsStream(path));
//            spriteProcessor = new SpriteProcessor(image, SCALE);
//            LOG.printWithLevel(10, "LOADED SUCCESSFULLY");
//        } catch (Exception e) {
//            LOG.printWithLevel(0, "FAILED TO LOAD -> "+path);
//            LOG.printStackTrace(e);
//        }
//    }

    // ====================  SETTERS  ====================

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
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }
}
