package Input;

import java.awt.event.KeyListener;

public interface GameInputHandler {
    Key up = new Key();
    Key down = new Key();
    Key left = new Key();
    Key right = new Key();

    void switchKey(int keyCode, boolean isPressed);
    void toggleKey(int keyCode);
    void checkAction();
}
