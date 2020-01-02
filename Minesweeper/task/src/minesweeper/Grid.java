package minesweeper;

import java.util.*;

class Grid {
    private GameSquare[][] grid;
    private int rows;
    private int cols;

    Grid(int rows, int cols) {
        grid = new GameSquare[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    void populateGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new GameSquare();
            }
        }
    }

    GameSquare getSquareAtIndex(int index) {
        int row = index / 9;
        int col = index % 9;
        return grid[row][col];
    }

    Set<Integer> getAdjacentSquares(int index) {
        int row = index / 9;
        int col = index % 9;
        Set<Integer> adjacentSquareIndices = new HashSet<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i > 8 || j < 0 || j > 8 || (i == row && j == col)) {
                    continue;
                }
                adjacentSquareIndices.add(i * 9 + j);
            }
        }
        return adjacentSquareIndices;
    }

    void printGrid(boolean gameFinished) {
        System.out.println(" │123456789│");
        System.out.println("—│—————————│");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1 +"│");
            for (int j = 0; j < cols; j++) {
                printSquare(i, j, gameFinished);
            }
            System.out.println("│");
        }
        System.out.println("—│—————————│");
    }

    private void printSquare(int row, int col, boolean gameFinished) {
        GameSquare square = grid[row][col];
        boolean squareIsHidden = !square.isVisible();
        if (square.isBomb() && gameFinished) {
            System.out.print("X");
        } else if (square.isMarked() && squareIsHidden) {
            System.out.print("*");
        } else if (squareIsHidden) {
            System.out.print(".");
        } else {
            int adjacentBombs = square.getAdjacentBombCount();
            System.out.print(adjacentBombs == 0 ? "/" : adjacentBombs);
        }
    }
}