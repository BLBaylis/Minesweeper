package minesweeper;

public class SafeSquare extends GameSquare {
    private Integer adjacentBombs;

    SafeSquare(int index, Grid grid) {
        super(index, grid);
    }

    SafeSquare(int row, int col, Grid grid) {
        super(row, col, grid);
    }

    private int countAdjacentBombs() {
        int adjacentBombsCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (grid.getSquareValue(i, j) instanceof BombSquare) {
                    adjacentBombsCount++;
                }
            }
        }
        return adjacentBombsCount;
    }

    @Override
    public String toString() {
        if (adjacentBombs == null) {
            adjacentBombs = countAdjacentBombs();
        }
        return adjacentBombs == 0 ? "." : Integer.toString(adjacentBombs);
    }
}
