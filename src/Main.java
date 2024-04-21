/*
    * Name: Banula Perera
    * IIT ID: 20212085
    * UoW ID: W1871527
    * -------------------------References-------------------------
    * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
    * https://www.javatpoint.com/shortest-path-in-a-binary-maze-in-java#
    * https://www.youtube.com/watch?v=t2d-XYuPfg0
    * https://www.youtube.com/watch?v=SZXXnB7vSm4
    * https://introcs.cs.princeton.edu/java/11style/
    * -----------------------------------------------------------
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.util.*;

public class Main {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Right, Left, Down, Up directions respectively.

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nWelcome to the Sliding Puzzle Game!");
            System.out.println("\nPlease share the file path with me:");
            String path = scanner.nextLine();

            try {
                // Read the maze from the file.
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                String line;
                int heightOfTheMaze = 0, widthOfTheMaze = 0;
                int startPointY = 0, startPointX = 0, finishPointY = 0, finishPointX = 0;
                boolean isValid = true, startPointFound = false, finishPointFound = false;

                while ((line = bufferedReader.readLine()) != null && isValid) {
                    widthOfTheMaze = heightOfTheMaze == 0 ? line.length() : widthOfTheMaze; // Set the width of the maze, if the height is 0.
                    heightOfTheMaze++;

                    // Validate the maze format.
                    for (int colX = 0; colX < widthOfTheMaze; colX++) {
                        char cell = line.charAt(colX);
                        if (cell == 'S') {
                            startPointY = heightOfTheMaze - 1;
                            startPointX = colX;
                            startPointFound = true;
                        } else if (cell == 'F') {
                            finishPointY = heightOfTheMaze - 1;
                            finishPointX = colX;
                            finishPointFound = true;
                        } else if (cell != '0' && cell != '.') {
                            System.out.println("\nInvalid characters found in the maze format.");
                            isValid = false;
                            break;
                        }
                    }
                }

                // Validate the maze has starting point and the ending point.
                if (isValid && (!startPointFound || !finishPointFound)) {
                    System.out.println((!startPointFound ? "Starting" : "Ending") + " point is missing in the maze.");
                    isValid = false;
                }

                if (isValid) {
                    String[][] maze = new String[heightOfTheMaze][widthOfTheMaze];

                    // Read the maze again to store the maze in a 2D array.
                    bufferedReader.close();
                    bufferedReader = new BufferedReader(new FileReader(path));

                    int row = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        maze[row++] = line.split("");
                    }
                    dijkstra(maze, startPointX, startPointY, finishPointX, finishPointY);
                }
            } catch (FileNotFoundException e) {
                System.out.println("\nFile not found. Please check the file path and try again.");
            } catch (Exception e) {
                System.out.println("\nAn error occurred while reading the file. Please try again.");
            }
        }
    }

    /*
        * Dijkstra's algorithm to find the shortest path from the start to the finish.
        * @param maze The maze to traverse.
        * @param startX The x-coordinate of the starting point.
        * @param startY The y-coordinate of the starting point.
        * @param finishX The x-coordinate of the finish point.
        * @param finishY The y-coordinate of the finish point.
        * print the path from start to finish and time taken to execute the algorithm.
     */
    public static void dijkstra(String[][] maze, int startX, int startY, int finishX, int finishY) {
        Instant start = Instant.now(); // Get the start time to execute the Dijkstra's algorithm.
        Node[][] nodes = new Node[maze.length][maze[0].length];

        // Initialize the nodes by removing the wall '0'.
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (!Objects.equals(maze[y][x], "0")) {
                    nodes[y][x] = new Node(x, y, Integer.MAX_VALUE, null);
                }
            }
        }

        // Initialize the start node by changing the distance of the start node 0.
        PriorityQueue<Node> queue = new PriorityQueue<>();
        nodes[startY][startX].distance = 0;
        queue.add(nodes[startY][startX]);

        // Filling the queue by comparing the distances of the nodes.
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : getNeighbors(current, nodes)) {
                int distance = current.distance + 1;
                if (distance < neighbor.distance) {
                    queue.remove(neighbor);
                    neighbor.distance = distance;
                    neighbor.previous = current;
                    queue.add(neighbor);
                }
            }
        }

        // Print the path from the start to the finish.
        if (nodes[finishY][finishX].distance != Integer.MAX_VALUE) {
            List<Node> path = new ArrayList<>();
            Node current = nodes[finishY][finishX];

            // Get the path from the finish to the start.
            while (current != null) {
                path.add(current);
                current = current.previous;
            }
            Collections.reverse(path); // Reverse the path to get the path from the start to the finish.
            printPath(path);
            Instant end = Instant.now(); // Get the end time to execute the Dijkstra's algorithm.
            System.out.println("\nTime taken to execute the algorithm: " + (end.toEpochMilli() - start.toEpochMilli()) + " milliseconds."); // Print the time taken to execute the algorithm.
        } else {
            System.out.println("No path found.");
        }
    }

    /*
        * Get the neighbors of the current node.
        * @param node The current node.
        * @param nodes The nodes in the maze.
        * @return The neighbors of the current node.
     */
    public static List<Node> getNeighbors(Node node, Node[][] nodes) {
        List<Node> neighbors = new ArrayList<>();

        // Get the neighbors of the current node.
        for (int[] direction : DIRECTIONS) {
            int x = node.x + direction[0];
            int y = node.y + direction[1];
            if (x >= 0 && x < nodes[0].length && y >= 0 && y < nodes.length && nodes[y][x] != null) {
                neighbors.add(nodes[y][x]);
            }
        }
        return neighbors;
    }

    /*
        * Print the path from the start to the finish.
        * @param path The path from the start to the finish.
     */
    public static void printPath(List<Node> path) {
        // Print the path from the start to the finish.
        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                System.out.println("\n" + (i + 1) + ". Start at (" + (path.get(i).y + 1) + "," + (path.get(i).x + 1) + ")");
            } else {
                Node current = path.get(i);
                Node previous = path.get(i - 1);
                if (current.x < previous.x) {
                    System.out.println((i + 1) + ". Move left to (" + (current.y + 1) + "," + (current.x + 1) + ")");
                } else if (current.x > previous.x) {
                    System.out.println((i + 1) + ". Move right to (" + (current.y + 1) + "," + (current.x + 1) + ")");
                } else if (current.y < previous.y) {
                    System.out.println((i + 1) + ". Move up to (" + (current.y + 1) + "," + (current.x + 1) + ")");
                } else if (current.y > previous.y) {
                    System.out.println((i + 1) + ". Move down to (" + (current.y + 1) + "," + (current.x + 1) + ")");
                }
            }
        }
        System.out.println("Done!");
    }
}