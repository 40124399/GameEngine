package Launchers;

import GUI.Game;
import Utility.Configurations;

public class Main {

    public static void main(String[] args) {

        Configurations.load(false);

        boolean testing = Boolean.parseBoolean(args[0]);
        boolean drawFrameStats = Boolean.parseBoolean(args[1]);

        new Game(testing,drawFrameStats).start();
    }

}

