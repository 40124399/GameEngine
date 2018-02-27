package Sprites;

/**
 * EMPTY FOR NOW
 */
public enum Positioning {
    FACING_FORWARD,
    FACING_BACKWARD,
    FACING_LEFT,
    FACING_RIGHT,

    WALKING_FORWARD,
    WALKING_BACKWARD,
    WALKING_LEFT,
    WALKING_RIGHT,

    RUNNING_FORWARD,
    RUNNING_BACKWARD,
    RUNNING_LEFT,
    RUNNING_RIGHT;

    @Override
    public String toString() {
        return this.name();
    }
}
