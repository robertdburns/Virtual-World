import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

public abstract class Movable extends ActiveAnimatedEntity {

    public Movable(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos) {

        // PATHING STRATEGIES


        SingleStepPathingStrategy SSPS = new SingleStepPathingStrategy();
        AStarPathingStrategy aStar = new AStarPathingStrategy();


        // LAMBDAS

        Predicate<Point> canPassThrough = (p1) -> !isInvalidMove(world, p1);


        BiPredicate<Point, Point> withinReach =
                (p1, p2) -> {
            Point p1L = new Point(p1.x - 1, p1.y);
            Point p1R = new Point(p1.x + 1, p1.y);
            Point p1T = new Point(p1.x, p1.y + 1);
            Point p1B = new Point(p1.x, p1.y - 1);
            return ( (Objects.equals(p2, p1L)) || (Objects.equals(p2, p1R)) || (Objects.equals(p2, p1T)) || (Objects.equals(p2, p1B)) );
        };

        Function<Point, Stream<Point>> potentialNeighbors = (p1) -> {
            Point p1L = new Point(p1.x - 1, p1.y);
            Point p1R = new Point(p1.x + 1, p1.y);
            Point p1T = new Point(p1.x, p1.y + 1);
            Point p1B = new Point(p1.x, p1.y - 1);
            return Stream.of(p1L, p1R, p1T, p1B);
        };


        // EXECUTE PATHING STRATEGY



        //List<Point> retList = SSPS.computePath(this.getPosition(), destPos,canPassThrough, withinReach, potentialNeighbors);                // Single Step

        List<Point> retList = aStar.computePath(this.getPosition(), destPos, canPassThrough, withinReach, potentialNeighbors);              // A* Pathing

        if (retList.isEmpty()) {
            return this.getPosition();
        }
        else {
            return retList.getFirst();
        }







    }
    public abstract boolean moveTo(WorldModel model, Entity target, EventScheduler scheduler);

    /**
     * The entity can move to destination if it's not occupied.
     */
    public boolean isInvalidMove(WorldModel world, Point destination) {
        return world.isOccupied(destination) && world.withinBounds(destination);
    }
}
