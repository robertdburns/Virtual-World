import processing.core.PImage;

import java.util.List;

public class SleepyCat extends Entity implements Animatable {

    public double animationPeriod;


    public SleepyCat(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    @Override
    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
}
