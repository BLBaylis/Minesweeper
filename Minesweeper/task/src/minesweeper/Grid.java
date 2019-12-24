package minesweeper;

import java.util.Arrays;

public class Grid {
    GameSquare[][] grid;
    private int rows;
    private int cols;

    Grid(int size) {
        grid = new GameSquare[size][size];
        this.rows = size;
        this.cols = size;
    }

    Grid(int rows, int cols) {
        grid = new GameSquare[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    void setSquareValue(int index, GameSquare value) {
        int row = index/cols;
        int col = index % cols;
        grid[row][col] = value;
    }

    void setSquareValue(int row, int col, GameSquare value) {
        grid[row][col] = value;
    }

    public GameSquare getSquareValue(int index) {
        int row = index/cols;
        int col = index % cols;
        return grid[row][col];
    }

    public GameSquare getSquareValue(int row, int col) {
        return grid[row][col];
    }

    void printGrid() {
        for (GameSquare[] row : grid) {
            for (int i = 0; i < row.length; i++) {
                System.out.print(row[i].toString());
            }
            System.out.println();
        }
    }
}
