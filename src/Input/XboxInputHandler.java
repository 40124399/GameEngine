package Input;

import GUI.Game;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class XboxInputHandler implements GameInputHandler {

    //All inputs
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public List<Key> keys = Arrays.asList(up,down,left,right);

    public XboxInputHandler(Game game) {
    }

    @Override
    public void switchKey(int keyCode, boolean isPressed) {

    }

    @Override
    public void toggleKey(int keyCode) {

    }

    @Override
    public void checkAction() {

    }

}
