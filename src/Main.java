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

/**
 * The Main class contains the main method to execute the Sliding Puzzle Game.
 */
public class Main {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Up, Down, Right, Left directions respectively.

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nWelcome to the Sliding Puzzle Game!");
            System.out.print("Please share the file path with me: ");
            String path = scanner.nextLine();

            try {
                // Read the maze from the file.
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                String line;
                // Variables to store the height, width, start point, and finish point of the maze.
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

    /**
     * Dijkstra's algorithm to find the shortest path from the start point "S" to the finish point "F".
     *
     * @param maze    The maze.
     * @param startX  The x-coordinate of the start point "S".
     * @param startY  The y-coordinate of the start point "S".
     * @param finishX The x-coordinate of the finish point "F".
     * @param finishY The y-coordinate of the finish point "F".
     */
    public static void dijkstra(String[][] maze, int startX, int startY, int finishX, int finishY) {
        Instant start = Instant.now(); // Get the start time to execute the Dijkstra's algorithm.
        Node[][] nodes = new Node[maze.length][maze[0].length];

        // Initialize the nodes in the maze.
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                nodes[y][x] = new Node(x, y, Integer.MAX_VALUE, null, maze[y][x].equals("0"));
            }
        }

        // Initialize the start node by assigning the distance of the start node 0.
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        nodes[startY][startX].distance = 0;
        queue.add(nodes[startY][startX]);
        boolean[][] visited = new boolean[maze.length][maze[0].length];

        // Traverse the maze using Dijkstra's algorithm.
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Skip this node if it has already been visited
            if (visited[current.y][current.x]) {
                continue;
            }

            visited[current.y][current.x] = true; // Mark the current node as visited

            for (Node neighbor : getNeighbors(current, nodes, maze)) {
                if (!visited[neighbor.y][neighbor.x]) {
                    neighbor.distance = current.distance + neighbor.distance; // Update the distance of the neighbor
                    neighbor.previous = current; // Set the parent node of the neighbor
                    queue.add(neighbor);
                }
            }
        }

        // Print the path from start to the finish.
        if (nodes[finishY][finishX].distance != Integer.MAX_VALUE) {
            List<Node> path = new ArrayList<>();
            Node current = nodes[finishY][finishX];

            // Get the path from finish to start.
            while (current != null) {
                path.add(current);
                current = current.previous;
            }
            Instant end = Instant.now(); // Get the end time to execute the Dijkstra's algorithm.
            Collections.reverse(path); // Reverse the path to get the path from the start to the finish.
            printPath(path);
            System.out.println("Time taken to execute the algorithm: " + (end.toEpochMilli() - start.toEpochMilli()) + " milliseconds."); // Print the time taken to execute the algorithm.
            System.out.print("\nDo you want to see the visual traveled path of the maze(Y/N)?: ");
            try (Scanner scanner = new Scanner(System.in)) {
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    visualPath(path, maze);
                }
            }
            System.out.println("\nThank you for playing the Sliding Puzzle Game!");
        } else {
            System.out.println("No path found.");
        }
    }

    /**
     * Get the neighbors with their respective distance of the current node.
     *
     * @param node  The current node.
     * @param nodes The nodes in the maze.
     * @param maze  The maze.
     * @return The list of neighbors of the current node.
     */
    public static List<Node> getNeighbors(Node node, Node[][] nodes, String[][] maze) {
        List<Node> neighbors = new ArrayList<>();
        int mazeHeight = nodes.length;
        int mazeWidth = nodes[0].length;

        // Traverse in all four directions to get the neighbors.
        for (int[] direction : DIRECTIONS) {
            int x = node.x;
            int y = node.y;
            int distanceTravel = 0;

            // Keep sliding in the chosen direction until hitting a wall or a rock.
            while (true) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                // Break if the new position is out of bounds or is a rock.
                if (newX < 0 || newX >= mazeWidth || newY < 0 || newY >= mazeHeight || nodes[newY][newX].isRock) {
                    break;
                }

                // Break if the new position is the finish point.
                if (maze[newY][newX].equals("F")) {
                    nodes[newY][newX].distance = distanceTravel;
                    neighbors.add(nodes[newY][newX]);
                    break;
                }
                x = newX;
                y = newY;
                distanceTravel++;
            }

            // Add the final position to the neighbors.
            if (x != node.x || y != node.y) {
                nodes[y][x].distance = distanceTravel;
                neighbors.add(nodes[y][x]);
            }
        }

        return neighbors;
    }

    /**
     * Print the path from the start to the finish.
     *
     * @param path The path from the start to the finish.
     */
    public static void printPath(List<Node> path) {
        // Print the path from the start to the finish.
        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                System.out.println("\n" + (i + 1) + ". Start at (" + (path.get(i).x + 1) + "," + (path.get(i).y + 1) + ")");
            } else {
                Node current = path.get(i);
                Node previous = path.get(i - 1);
                if (current.x < previous.x) {
                    System.out.println((i + 1) + ". Move left to (" + (current.x + 1) + "," + (current.y + 1) + ")");
                } else if (current.x > previous.x) {
                    System.out.println((i + 1) + ". Move right to (" + (current.x + 1) + "," + (current.y + 1) + ")");
                } else if (current.y < previous.y) {
                    System.out.println((i + 1) + ". Move up to (" + (current.x + 1) + "," + (current.y + 1) + ")");
                } else if (current.y > previous.y) {
                    System.out.println((i + 1) + ". Move down to (" + (current.x + 1) + "," + (current.y + 1) + ")");
                }
            }
        }
        System.out.println("Done!");
    }

    /**
     * Visualize the path in the maze.
     *
     * @param path The path from the start to the finish.
     * @param maze The maze.
     */
    public static void visualPath(List<Node> path, String[][] maze) {
        // Mark the path in the maze.
        for (Node node : path) {
            if (maze[node.y][node.x].equals(".") || maze[node.y][node.x].equals("0")) {
                maze[node.y][node.x] = "*";
            }
        }

        // Print the maze with the path.
        for (String[] row : maze) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}