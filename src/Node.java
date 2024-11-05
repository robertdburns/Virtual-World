import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Node {

    // INSTANCE VARIABLES

    private Point pos;
    private int rootDistance;
    private int destDistance;
    private Node prev;


    // CONSTRUCTOR
    public Node(Point pos, int rootDistance, int destDistance, Node prev) {
        this.pos = pos;
        this.rootDistance = rootDistance;
        this.prev = prev;
        this.destDistance = destDistance;
    }

    // GETTERS / SETTERS

    public int getDestDistance() {
        return destDistance;
    }

    public int getRootDistance() {
        return rootDistance;
    }

    public Node getPrev() {
        return prev;
    }

    public Point getPos() {
        return pos;
    }



    // INSTANCE AND STATIC METHODS

    public int fScore() {
        return rootDistance + destDistance;
    }



    public static int Manhattan(Point goal, Point current) {
        int xDistance = Math.abs(goal.x - current.x);
        int yDistance = Math.abs(goal.y - current.y);
        return xDistance + yDistance;
    }

    public static List<Point> Neighbors(Node current) {
        List<Point> retList = new ArrayList<>();
        Point currentPoint = current.pos;
        retList.add(new Point(currentPoint.x - 1, currentPoint.y));                   // Left Point
        retList.add(new Point(currentPoint.x + 1, currentPoint.y));                   // Right Point
        retList.add(new Point(currentPoint.x, currentPoint.y + 1));                   // Top Point
        retList.add(new Point(currentPoint.x, currentPoint.y - 1));                   // Bottom Point
        return retList;
    }

    public boolean adjacentTo (Point dest) {
        Point left = new Point(this.pos.x - 1, this.pos.y);
        Point right = new Point(this.pos.x + 1, this.pos.y);
        Point top = new Point(this.pos.x, this.pos.y - 1);
        Point bottom = new Point(this.pos.x, this.pos.y + 1);


        return ( (left.equals(dest)) || (right.equals(dest)) || (top.equals(dest)) || (bottom.equals(dest)));
    }


    // TO STRING AND EQUALS
    @Override
    public String toString() {
        return "Node at " + pos + " root: " + rootDistance + " destination: " + destDistance;
    }

    @Override
    public boolean equals(Object other) {

        return (other instanceof Node && (this.getPos().equals(((Node) other).getPos())));

//        if (other instanceof Node) {
//            return (this.pos == ((Node) other).pos);
//        }
//        else {
//            return false;
//        }
    }



}
