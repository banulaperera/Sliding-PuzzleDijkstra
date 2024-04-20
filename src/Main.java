/*
 * Student name - Banula Perera
 * IIT Number - 20212085
 * UOW Number - W1871527
 */

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the Sliding Puzzle Game!");
        System.out.println("\nPlease share the file path with me:");
        String path = scanner.nextLine();

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
        }
    }
}