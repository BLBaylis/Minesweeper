package minesweeper;

public class GameSquare {
    int row;
    int col;
    Grid grid;

    GameSquare(int index, Grid grid) {
        int cols = grid.getCols();
        this.grid = grid;
        this.row = index/cols;
        this.col = index % cols;
    }

    GameSquare(int row, int col, Grid grid) {
        this.grid = grid;
        this.row = row;
        this.col = col;
    }
}
