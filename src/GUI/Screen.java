package GUI;

import Entities.PlaceholderPlayer;

public class Screen {
    public static final int MAP_WIDTH = 64;
    public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

    public int[] tiles = new int[MAP_WIDTH*MAP_WIDTH];
    public int[] colors = new int[MAP_WIDTH*MAP_WIDTH*4];

    public int xOffset = 0;
    public int yOffset = 0;
    public int width;
    public int height;

    public PlaceholderPlayer player;

    public Screen(int width, int height, PlaceholderPlayer player) {
        this.width = width;
        this.height = height;
        this.player = player;

        for(int i = 0; i < MAP_WIDTH * MAP_WIDTH; i ++) {
            colors[i*4] = 0xff00ff;
            colors[i*4 + 1] = 0x00ffff;
            colors[i*4 + 2] = 0xffff00;
            colors[i*4 + 3] = 0xffffff;
        }
    }

    public void render(int[] pixels, int offset, int row) {
        for(int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++) {
            int yMin = yTile * 8 - yOffset;
            int yMax = yMin + 8;

            if(yMin < 0) yMin = 0;
            if(yMax > height) yMax = height;

            for(int xTile = yOffset >> 3; xTile  <= (yOffset + height) >> 3; xTile ++) {
                int xMin = xTile  * 8 - yOffset;
                int xMax = xMin + 8;

                if(xMin < 0) xMin = 0;
                if(xMax > height) xMax = height;

                int tileIndex = (xTile & (MAP_WIDTH_MASK)) + (yTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;
            }

            for (int y = yMin; y)
        }
    }
}
