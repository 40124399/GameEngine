package Input;

public class Key {

    private boolean pressed =  false;

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void togglePressed() {
        pressed = !pressed;
    }

    public boolean isPressed() {
        return this.pressed;
    }
}
