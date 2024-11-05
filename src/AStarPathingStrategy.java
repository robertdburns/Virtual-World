import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy{
    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {

        List<Point> Path = new ArrayList<Point>();

        PriorityQueue<Node> openList = new PriorityQueue<Node>(Comparator.comparingInt(Node::fScore));                  // initalize open list of Nodes to be visited
        openList.add(new Node(start, 0, Node.Manhattan(start, end), null));                             // add start node

        Set<Point> closedList = new HashSet<Point>();                                                                   // initalize closed list for Points visited

        while (!openList.isEmpty()) {                                                                                   // iterate while still have to move
            Node current = openList.poll();                                                                             // remove current node from open set...
            closedList.add(current.getPos());                                                                           // and add current's position to the closed list

            List<Node> tempList = new ArrayList<>();

            if (!current.adjacentTo(end)) {                                                                             // if the point is not next to the end, add all valid adjacent points
                potentialNeighbors.apply(current.getPos())
                        .filter(canPassThrough)
                        .filter(p -> !closedList.contains(p))
                        .map(p -> new Node(p, current.getRootDistance() + 1, Node.Manhattan(p, end), current))           // NEED TO CHANGE TRUE TO ACTUALLY DETERMINE IF IT IS
                        .forEach(tempList::add);                                                                                    // all valid nodes added to TEMP list


                for (Node n : tempList) {                                                                               // for each of the nodes to be added
                    if (openList.contains(n)) {                                                                         // CONTAINS (Y)
                        if ( openList.removeIf(p -> ( n.getRootDistance() < p.getRootDistance() && n.equals(p))) ) {    // n (new) IS BETTER THAN p (current) (Y)
                            if (n.getPos().x > 0 && n.getPos().y > 0 && n.getPos().x < 40 && n.getPos().y < 30)  {
                                openList.add(n);
                            }
                        }
                    }

                    else {                                                                                              // CONTAINS (N)
                        if (n.getPos().x > 0 && n.getPos().y > 0 && n.getPos().x < 40 && n.getPos().y < 30) {
                            openList.add(n);
                        }
                    }
                }


            }

            else {                                                                                                      // current position is adjacent to end point
                Path.add(current.getPos());
                Node temp = current.getPrev();                                                                          // make a copy of the current node to iterate over and destroy
                while (temp.getPrev() != null) {                                                                                  // iterate down previous nodes until back to original node
                    Path.add(temp.getPos());                                                                            // add current Point to the path
                    temp = temp.getPrev();                                                                              // iterate to next Node
                }

                return Path.reversed();                                                                                 // return path if adjacent to target
            }

        }

        List<Point> noPath = new ArrayList<>();
        return noPath;                                                                                                  // if no path exists return empty list
    }








}
