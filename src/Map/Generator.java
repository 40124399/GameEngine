package Map;

import Entities.PlaceholderPlayer;
import Sprites.SpriteLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

public class Generator {
    private int width;
    private int height;
    private int scale;

    private BufferedImage floorTile;
    private BufferedImage bigMap;

    private List<List<BufferedImage>> map;

    public Generator(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        this.floorTile = SpriteLoader.loadFromAbsolutePath("C:/Users/James/Documents/assets/floorTile.png");
        this.bigMap = SpriteLoader.loadFromAbsolutePath("C:/Users/James/Documents/assets/shitMap.png");

        this.map = generateMap();
    }

    public void draw(Graphics g, PlaceholderPlayer player) {
        g.drawImage(getMapSection(player), 0, 0, null, null);
    }

    private BufferedImage getMapSection(PlaceholderPlayer player) {
        int left = player.getX()-(width/2*scale);
        int top = player.getY()-(height/2*scale);
        if(left < 0) left = 0;
        if(top < 0) top = 0;
        return bigMap.getSubimage(left, top, 100*scale, 100*scale);
    }

    private List<List<BufferedImage>> generateMap() {
        List<List<BufferedImage>> map = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            List<BufferedImage> level = new ArrayList<>();
            map.add(level);
            for (int j = 0; j < height; j++) {
                level.add(floorTile);
            }
        }
        return map;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<List<BufferedImage>> getMap() {
        return map;
    }

    public void setMap(List<List<BufferedImage>> map) {
        this.map = map;
    }
}
