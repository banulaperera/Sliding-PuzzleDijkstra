/*
 * Student name - Banula Perera
 * IIT Number - 20212085
 * UOW Number - W1871527
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Sliding Puzzle Game!");
        System.out.println("\nPlease share the file path with me:");
        String path = scanner.nextLine();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            int heightOfTheMaze = 0, widthOfTheMaze = 0;
            int startPointY = 0, startPointX = 0, finishPointY = 0, finishPointX = 0;
            boolean isValid = true, startPointFound = false, finishPointFound = false;

            while ((line = bufferedReader.readLine()) != null && isValid) {
                widthOfTheMaze = heightOfTheMaze == 0 ? line.length() : widthOfTheMaze;
                heightOfTheMaze++;

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

            if (isValid && (!startPointFound || !finishPointFound)) {
                System.out.println((!startPointFound ? "Starting" : "Ending") + " point is missing in the maze.");
                isValid = false;
            }

            if (isValid) {
                String[][] maze = new String[heightOfTheMaze][widthOfTheMaze];

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

    public static void dijkstra(String[][] maze, int startX, int startY, int finishX, int finishY) {
        Node[][] nodes = new Node[maze.length][maze[0].length];
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (!Objects.equals(maze[y][x], "0")) {
                    nodes[y][x] = new Node(x, y, Integer.MAX_VALUE, null);
                }
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        nodes[startY][startX].distance = 0;
        queue.add(nodes[startY][startX]);

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

        if (nodes[finishY][finishX].distance != Integer.MAX_VALUE) {
            List<Node> path = new ArrayList<>();
            Node current = nodes[finishY][finishX];
            while (current != null) {
                path.add(current);
                current = current.previous;
            }
            Collections.reverse(path);
            printPath(path);
        } else {
            System.out.println("No path found.");
        }
    }

    public static List<Node> getNeighbors(Node node, Node[][] nodes) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int x = node.x + direction[0];
            int y = node.y + direction[1];
            if (x >= 0 && x < nodes[0].length && y >= 0 && y < nodes.length && nodes[y][x] != null) {
                neighbors.add(nodes[y][x]);
            }
        }
        return neighbors;
    }

    public static void printPath(List<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                System.out.println((i + 1) + ". Start at (" + (path.get(i).y + 1) + "," + (path.get(i).x + 1) + ")");
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