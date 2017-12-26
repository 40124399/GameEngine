package Entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PlaceholderPlayer {

    public String path;
    public int width;
    public int height;
    public int[] pixels;

    public PlaceholderPlayer(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(PlaceholderPlayer.class.getResourceAsStream(path));
        } catch (Exception e) {
            image = null;
            e.printStackTrace();
        }
         if(image == null) return;

        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();

        pixels = image.getRGB(0,0,width,height,null, 0, width);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = (pixels[i] & 0xff) / 64;
        }

        for(int i = 0; i < 8; i++) {
            System.out.println(pixels[i]);
        }
    }
}
