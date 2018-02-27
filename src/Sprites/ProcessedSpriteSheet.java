package Sprites;

import Entities.PlaceholderPlayer;
import Utility.Logging;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessedSpriteSheet <T> {

    private static final Logging LOG = new Logging(ProcessedSpriteSheet.class);

    private Map<T, List<BufferedImage>> processedSpriteSheet = new HashMap<>();

    public void addOrPut(T t, BufferedImage image) {
        if(processedSpriteSheet.containsKey(t)) {
            addImage(t, image);
        }else {
            put(t);
            addImage(t, image);
        }
    }

    public void put(T t) {
        processedSpriteSheet.put(t, new ArrayList<BufferedImage>());
    }

    public void addImage(T t, BufferedImage image) {
        processedSpriteSheet.get(t).add(image);
    }

    public List<BufferedImage> getArray(T t) {
        return processedSpriteSheet.get(t);
    }

    public BufferedImage getImage(T t, int i) {
        try{
            BufferedImage image = processedSpriteSheet.get(t).get(i);
            return image;
        } catch(NullPointerException npe) {
            LOG.printWithLevel(3, "RETURNING NULL; CRITICAL ERROR! " + t + " - " + i);
            return null;
        }
    }


}
