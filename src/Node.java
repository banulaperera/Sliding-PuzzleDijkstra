public class Node implements Comparable<Node> {
    int x, y; // The x and y coordinates of the node.
    int distance; // The distance from the start node to the current node.
    Node previous; // The previous node in the path.

    public Node(int x, int y, int distance, Node previous) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.previous = previous;
    }

    // Compare the distance of the current node to the distance of another node.
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}
