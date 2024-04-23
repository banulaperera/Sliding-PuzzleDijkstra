/**
 * Node class to represent a node in the grid
 * <p>
 * The node has the following attributes:
 * - x, y: coordinates of the node
 * - distance: distance of the node
 * - previous: parent node
 * - isRock: check whether the node is a rock or not
 * <p>
 * The node implements the Comparable interface to compare the distance of the node
 * <p>
 * The node has a constructor to initialize the node with the given attributes
 * <p>
 * The node has a compareTo method to compare the distance of the node
 */
public class Node implements Comparable<Node> {
    int x, y; // coordinates of the node
    int distance; // distance of the node
    Node previous; // parent node
    boolean isRock; // check whether the node is a rock or not

    public Node(int x, int y, int distance, Node previous, boolean isRock) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.previous = previous;
        this.isRock = isRock;
    }

    /**
     * Compare the distance of the node
     *
     * @param other the other node to compare
     * @return the comparison result
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}