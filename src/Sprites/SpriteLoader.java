package Sprites;

import Utility.Logging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SpriteLoader {

    private static final Logging LOG = new Logging(SpriteLoader.class);

    public static BufferedImage load(String path) {
        BufferedImage image = null;
        LOG.printWithLevel(10,"ATTEMPTING TO LOAD -> "+path);
        try {
            image = ImageIO.read(SpriteLoader.class.getResource(path));
            LOG.printWithLevel(10,"IMAGE LOADED SUCCESSFULLY");
        } catch(IOException e) {
            LOG.printWithLevel(-1,"FAILED TO LOAD IMAGE -> "+path);
            LOG.printStackTrace(e);
        }
        return image;
    }

    public static BufferedImage loadFromAbsolutePath(String path) {
        BufferedImage image = null;
        LOG.printWithLevel(10,"ATTEMPTING TO LOAD -> "+path);
        try {
            image = ImageIO.read(new File(path));
            LOG.printWithLevel(10,"IMAGE LOADED SUCCESSFULLY");
        } catch(IOException e) {
            LOG.printWithLevel(-1,"FAILED TO LOAD IMAGE -> "+path);
            LOG.printStackTrace(e);
        }
        return image;
    }
}
