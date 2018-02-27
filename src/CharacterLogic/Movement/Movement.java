package CharacterLogic.Movement;

import Sprites.Positioning;
import Utility.Logging;

import java.util.HashMap;
import java.util.Map;

import static Sprites.Positioning.*;

public class Movement {

    private static final Logging LOG = new Logging(Movement.class);

    private boolean moving;
    private double accelerationBase;
    private double decelerationBase;
    private double yVelocity;
    private double xVelocity;
    private double maxVelocity;
    private int cordX;
    private int cordY;

    private int frame;

    private Map<Positioning, Positioning> stoppingPositionMap;
    private Map<Positioning, Positioning> movingPositionMap;

    public Movement(double accelerationBase, double decelerationBase, double maxVelocity, int cordX, int cordY) {
        this.accelerationBase = accelerationBase;
        this.decelerationBase = decelerationBase;
        this.maxVelocity = maxVelocity;
        this.cordX = cordX;
        this.cordY = cordY;

        this.moving = false;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.frame = 0;

        stoppingPositionMap = prepStoppingMap();
        movingPositionMap = prepMovingMap();
    }

    public Positioning determinePosition(Positioning position) {
        if(yVelocity == xVelocity && position.name().startsWith("FACING")) {
            return movingPositionMap.get(position);
        }
        if (yVelocity == 0 && xVelocity == 0) {
            return stoppingPositionMap.get(position);
        }
        if(stoppingPositionMap.get(position) != position && Math.abs(yVelocity) == Math.abs(xVelocity)) {
            return position;
        }
        // below could be optimized
        if (yVelocity < 0 && Math.abs(xVelocity) < Math.abs(yVelocity)) {
            return WALKING_FORWARD;
        }
        if (yVelocity > 0 && Math.abs(xVelocity) < Math.abs(yVelocity)) {
            return WALKING_BACKWARD;
        }
        if (xVelocity < 0 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
            return WALKING_LEFT;
        }
        if (xVelocity > 0 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
            return WALKING_RIGHT;
        }

        LOG.printWithLevel(1, "ALL CASES FAILED - BIG PROBLEM BOI (Y_VELOCITY="+yVelocity+" | X_VELOCITY="+xVelocity+")");

        return stoppingPositionMap.get(position); //SHOULDN'T HAPPEN
    }

    public void move(double delta, boolean forward, boolean backward, boolean left, boolean right) {
        double acceleration = accelerationBase * delta;
        double deceleration = decelerationBase * delta;

        if (forward || backward) {
            if (forward) {
                yVelocity = lerpNeg(maxVelocity, yVelocity, acceleration);
            }
            if (backward) {
                yVelocity = lerpPos(maxVelocity, yVelocity, acceleration);
            }
        } else {
            yVelocity = lerpDecelerate(0, yVelocity, deceleration);
        }

        if (left || right) {
            if (left) {
                xVelocity = lerpNeg(maxVelocity, xVelocity, acceleration);
            }
            if (right) {
                xVelocity = lerpPos(maxVelocity, xVelocity, acceleration);
            }
        } else {
            xVelocity = lerpDecelerate(0, xVelocity, deceleration);
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

    private static Map<Positioning, Positioning> prepStoppingMap() {
        Map<Positioning, Positioning> map = new HashMap<>();
        map.put(FACING_BACKWARD, FACING_BACKWARD);
        map.put(FACING_FORWARD, FACING_FORWARD);
        map.put(FACING_LEFT, FACING_LEFT);
        map.put(FACING_RIGHT, FACING_RIGHT);

        map.put(WALKING_BACKWARD, FACING_BACKWARD);
        map.put(WALKING_FORWARD, FACING_FORWARD);
        map.put(WALKING_LEFT, FACING_LEFT);
        map.put(WALKING_RIGHT, FACING_RIGHT);

        map.put(RUNNING_BACKWARD, FACING_BACKWARD);
        map.put(RUNNING_FORWARD, FACING_FORWARD);
        map.put(RUNNING_LEFT, FACING_LEFT);
        map.put(RUNNING_RIGHT, FACING_RIGHT);

        return map;
    }

    private static Map<Positioning, Positioning> prepMovingMap() {
        Map<Positioning, Positioning> map = new HashMap<>();
        map.put(FACING_BACKWARD, WALKING_BACKWARD);
        map.put(FACING_FORWARD, WALKING_FORWARD);
        map.put(FACING_LEFT, WALKING_LEFT);
        map.put(FACING_RIGHT, WALKING_RIGHT);

        map.put(WALKING_BACKWARD, WALKING_BACKWARD);
        map.put(WALKING_FORWARD, WALKING_FORWARD);
        map.put(WALKING_LEFT, WALKING_LEFT);
        map.put(WALKING_RIGHT, WALKING_RIGHT);

        map.put(RUNNING_BACKWARD, WALKING_BACKWARD);
        map.put(RUNNING_FORWARD, WALKING_FORWARD);
        map.put(RUNNING_LEFT, WALKING_LEFT);
        map.put(RUNNING_RIGHT, WALKING_RIGHT);

        return map;
    }

    public int getNextFrame(Positioning position, boolean forward, boolean backward, boolean left, boolean right) {
        if(position.name().matches("FACING*")) {
            frame = 0;
            return frame;
        }

        if((!forward && !backward && !left && !right)
                || (xVelocity == 0 && yVelocity == 0)
                || (frame == 8)) {
            frame = 0;
            return frame;
        }

        frame += 1;
        return frame;
    }

    public int getSafeFrame(boolean forward, boolean backward, boolean left, boolean right) {
        if((!forward && !backward && !left && !right)
                || (xVelocity == 0 && yVelocity == 0)
                || (frame == 8)) {
            frame = 0;
        }
        return frame;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public double getAccelerationBase() {
        return accelerationBase;
    }

    public void setAccelerationBase(double accelerationBase) {
        this.accelerationBase = accelerationBase;
    }

    public double getDecelerationBase() {
        return decelerationBase;
    }

    public void setDecelerationBase(double decelerationBase) {
        this.decelerationBase = decelerationBase;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
}
