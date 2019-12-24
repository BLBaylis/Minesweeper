package minesweeper;

public class BombSquare extends GameSquare {

    BombSquare(int index, Grid grid) {
        super(index, grid);
    }

    BombSquare(int row, int col, Grid grid) {
        super(row, col, grid);
    }

    @Override
    public String toString() {
        return "X";
    }
}
