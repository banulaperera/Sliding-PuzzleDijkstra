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
        System.out.println("Enter the path of the file: ");
        String path = scanner.nextLine();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line;
        int heightOfTheMaze = 0;
        int widthOfTheMaze = 0;
        int startY = 0, startX = 0, finishY = 0, finishX = 0;
        boolean valid = true;

        while ((line = bufferedReader.readLine()) != null) {
            if (heightOfTheMaze == 0) {
                widthOfTheMaze = line.length();
            }
            heightOfTheMaze++;

            for (int colX = 0; colX < widthOfTheMaze; colX++) {
                char cell = line.charAt(colX);
                if (cell == 'S') {
                    startY = heightOfTheMaze - 1;
                    startX = colX;
                } else if (cell == 'F') {
                    finishY = heightOfTheMaze - 1;
                    finishX = colX;
                } else if (cell != '0' && cell != '.') {
                    System.out.println("Invalid characters found in the maze format.");
                    System.out.println("Starting position must have ’S’, ending position must have ‘F’, rocks are ‘0’ and ice is ‘.’.");
                    valid = false;
                    break;
                }
            }

            if (!valid) {
                break;
            }
        }

        if (valid) {
            String[][] maze = new String[heightOfTheMaze][widthOfTheMaze];
        }
    }
}