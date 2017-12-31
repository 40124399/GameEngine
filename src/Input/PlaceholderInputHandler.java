package Input;

import GUI.Game;
import Utility.Logging;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This should use parameters loaded via a file and be configurable from an interface in game with save option.
 */
public class PlaceholderInputHandler implements KeyListener, GameInputHandler {

    private static final Logging LOG = new Logging(PlaceholderInputHandler.class);
    private Game game;

//    public List<Key> keys = Arrays.asList(up,down,left,right);

    public PlaceholderInputHandler(Game game) {
        this.game = game;
        game.addKeyListener(this);
        LOG.printWithLevel(10,"LISTENING");
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switchKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switchKey(e.getKeyCode(), false);
    }

    public void switchKey(int keyCode, boolean isPressed) {
        LOG.printWithLevel(10, keyCode + " " + isPressed);

        switch (keyCode) {
            case KeyEvent.VK_W:
                up.setPressed(isPressed);
                break;
            case KeyEvent.VK_S:
                down.setPressed(isPressed);
                break;
            case KeyEvent.VK_A:
                left.setPressed(isPressed);
                break;
            case KeyEvent.VK_D:
                right.setPressed(isPressed);
                break;
            case KeyEvent.VK_ESCAPE:
                exit.setPressed(isPressed);
                break;
        }
    }

    public void toggleKey(int keyCode) {
        LOG.printWithLevel(10, keyCode + " TOGGLING");

//        if(keyCode == KeyEvent.VK_W)up.togglePressed();
//        if(keyCode == KeyEvent.VK_S)down.togglePressed();
//        if(keyCode == KeyEvent.VK_A)left.togglePressed();
//        if(keyCode == KeyEvent.VK_D)right.togglePressed();
//        if(keyCode == KeyEvent.VK_ESCAPE)exit.togglePressed();

        switch (keyCode) {
            case KeyEvent.VK_W:
                up.togglePressed();
                break;
            case KeyEvent.VK_S:
                down.togglePressed();
                break;
            case KeyEvent.VK_A:
                left.togglePressed();
                break;
            case KeyEvent.VK_D:
                right.togglePressed();
                break;
            case KeyEvent.VK_ESCAPE:
                exit.togglePressed();
                break;
        }
    }

    public void checkAction() {

    }
}
