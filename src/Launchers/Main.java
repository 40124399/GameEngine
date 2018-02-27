package Launchers;

import GUI.Game;
import Utility.Configurations;

public class Main {

    public static void main(String[] args) {

        Configurations.load(false);

        boolean testing;
        boolean drawFrameStats;

        if(args.length > 2) {
            testing = Boolean.parseBoolean(args[0]);
            drawFrameStats = Boolean.parseBoolean(args[1]);
        } else {
            testing = true;
            drawFrameStats = true;
        }

        new Game(testing,drawFrameStats).start();
    }

}

