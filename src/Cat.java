import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Cat extends Movable{
    public Cat(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    private String mode = "active";

    public String getMode() {
        return mode;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (Objects.equals(mode, "active")) {
            Optional<Entity> catTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Fairy.class)));

            if (catTarget.isPresent()) {
                //Point tgtPos = catTarget.get().getPosition();

                if (this.moveTo(world, catTarget.get(), scheduler)) {
                    System.out.println("got the fairy");
                    mode = "sleeping";
                }
            }

            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
        }
        else {
            SleepyCat newSleepyCat = new SleepyCat("sleepy cat", this.getPosition(), imageStore.getImageList("catSleeping"), 0.5);
            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(newSleepyCat);
            newSleepyCat.scheduleActions(scheduler, world, imageStore);
        }


    }

    @Override
    public boolean moveTo(WorldModel model, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            model.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPosition(model, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                model.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
