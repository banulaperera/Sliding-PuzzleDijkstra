public class Node implements Comparable<Node> {
    int x, y;
    int distance;
    Node previous;
    boolean isRock;

    public Node(int x, int y, int distance, Node previous, boolean isRock) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.previous = previous;
        this.isRock = isRock;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}