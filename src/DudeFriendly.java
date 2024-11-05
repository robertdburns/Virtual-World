import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DudeFriendly extends Movable {

    public DudeFriendly(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Optional<Entity> DudeFriendlyTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Cat.class)));

        if (DudeFriendlyTarget.isPresent()) {

            if (DudeFriendlyTarget.get() instanceof Cat) {
                if (Objects.equals(((Cat) DudeFriendlyTarget.get()).getMode(), "Active")) {
                    if (this.moveTo(world, DudeFriendlyTarget.get(), scheduler)) {
                        System.out.println("got the fairy");
                    }
                }

                else {

                }

            }

            if (this.moveTo(world, DudeFriendlyTarget.get(), scheduler)) {
            }
        }

        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());



    }

    @Override
    public boolean moveTo(WorldModel model, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {


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
