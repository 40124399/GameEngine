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
        LOG.print("LISTENING");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        toggleKey(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode());
    }

    public void switchKey(int keyCode, boolean isPressed) {
        LOG.printWithLevel(10, keyCode+" "+isPressed);

        if(keyCode == KeyEvent.VK_W){}
        switch(keyCode){
            case KeyEvent.VK_W : up.setPressed(isPressed);
                break;
            case KeyEvent.VK_S : down.setPressed(isPressed);
                break;
            case KeyEvent.VK_A : left.setPressed(isPressed);
                break;
            case KeyEvent.VK_D : right.setPressed(isPressed);
                break;
        }
    }

    public void toggleKey(int keyCode) {
        LOG.printWithLevel(10, keyCode+" TOGGLING");

        if(keyCode == KeyEvent.VK_W){}
        switch(keyCode){
            case KeyEvent.VK_W : up.togglePressed();
                                break;
            case KeyEvent.VK_S : down.togglePressed();
                                break;
            case KeyEvent.VK_A : left.togglePressed();
                                break;
            case KeyEvent.VK_D : right.togglePressed();
                                break;
        }
    }

    public void checkAction() {

    }
}
