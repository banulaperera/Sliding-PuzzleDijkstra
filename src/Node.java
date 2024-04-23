/**
 * Node class to represent a node in the grid
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