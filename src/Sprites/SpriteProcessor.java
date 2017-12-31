package Sprites;

import Entities.PlaceholderPlayer;
import Utility.Logging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteProcessor {

    private static final Logging LOG = new Logging(SpriteProcessor.class);

    private BufferedImage sheet;
    private int scale;

    public SpriteProcessor(BufferedImage sheet) {
        this.sheet = sheet;
        this.scale = 16;
    }

    public SpriteProcessor(BufferedImage sheet, int scale) {
        this.sheet = sheet;
        this.scale = scale;
    }

    public SpriteProcessor(String path, int scale) {
        this.sheet = loadSheet(path);
        this.scale = scale;
    }

    public BufferedImage loadSheet(String path) {
        LOG.printWithLevel(10, "ATTEMPTING TO LOAD -> "+path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(SpriteProcessor.class.getResourceAsStream(path));
            LOG.printWithLevel(10, "LOADED SUCCESSFULLY");
        } catch (Exception e) {
            LOG.printWithLevel(0, "FAILED TO LOAD -> "+path);
            LOG.printStackTrace(e);
        }
        return image;
    }

    public BufferedImage crop (int col, int row, int w, int h) {
        return sheet.getSubimage(col * w, row * h, w, h);
    }

    public BufferedImage crop (int col, int row) {
        return sheet.getSubimage(col * scale, row * scale, scale, scale);
    }

    public void crop (int col, int row, int w, int h, ProcessedSpriteSheet processedSpriteSheet, Positioning position) {
        processedSpriteSheet.put(position, sheet.getSubimage(col * w, row * h, w, h));
    }

    public void crop (int col, int row, ProcessedSpriteSheet processedSpriteSheet, Positioning position) {
        processedSpriteSheet.put(position, sheet.getSubimage(col * scale, row * scale, scale, scale));
    }
}
