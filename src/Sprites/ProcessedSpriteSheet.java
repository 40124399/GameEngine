package Sprites;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ProcessedSpriteSheet <T> {

    private Map<T, BufferedImage> processedSpriteSheet = new HashMap<>();

    public void put(T t, BufferedImage image) {
        processedSpriteSheet.put(t, image);
    }

    public BufferedImage get(T t) {
        return processedSpriteSheet.get(t);
    }
}
