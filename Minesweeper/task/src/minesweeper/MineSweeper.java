package minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class MineSweeper {

    MineSweeper() {

    }

    final void run() {
        Scanner scanner = new Scanner(System.in);
        Integer bombNum = null;
        boolean firstMoveTaken = false;
        Grid grid = new Grid(9, 9);
        while (bombNum == null || bombNum < 0 || bombNum > 80) {
            System.out.println("How many mines do you want on the field? (1 - 80)");
            bombNum = scanner.nextInt();
            grid.setBombNum(bombNum);
        }
        grid.printInitialGrid();
        while (!firstMoveTaken || !grid.isGameWon()) {
            System.out.println("Set/delete mine marks (x and y coordinates): ");
            int col = scanner.nextInt();
            int row = scanner.nextInt();
            String moveType = scanner.next();
            if ("mine".equals(moveType)) {
                try {
                    grid.markSquare(row, col);
                } catch (IllegalArgumentException e) {
                    System.out.println("Already selected");
                }
            } else if ("free".equals(moveType)) {
                try {
                    grid.revealMove(row, col);
                    firstMoveTaken = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("There is a number here!");
                }
            } else {
                System.out.println("Incorrect syntax");
                continue;
            }
            if (grid.getIsGameLost()) {
                grid.printHiddenGrid();
                System.out.println("You stepped on a mine and failed!");
                return;
            }
            if (firstMoveTaken) {
                grid.printHiddenGrid();
            } else {
                grid.printInitialGrid();
            }
        }
        System.out.println("Congratulations! You found all mines!");
    }

}