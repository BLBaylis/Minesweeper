package minesweeper;

import java.util.Set;

class Grid {
    private GameSquare[][] grid;
    private int rows;
    private int cols;

    Grid(int rows, int cols) {
        grid = new GameSquare[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    void populateGrid(Set<Integer> bombIndices) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameSquare square = new GameSquare(bombIndices.contains(i * 9 + j));
                grid[i][j] = square;
            }
        }
    }

    private int countAdjacentBombs(int row, int col) {
        int adjacentBombsCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i > 8 || j < 0 || j > 8) {
                    continue;
                }
                boolean squareHasBomb = grid[i][j].getHasBomb();
                if (squareHasBomb) {
                    adjacentBombsCount++;
                }
            }
        }
        return adjacentBombsCount;
    }

    void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameSquare square = grid[i][j];
                int adjacentBombs = countAdjacentBombs(i, j);
                if (square.getHasBomb()) {
                    System.out.print("X");
                } else {
                    System.out.print(adjacentBombs == 0 ? "." : adjacentBombs);
                }
            }
            System.out.println();
        }
    }
}
