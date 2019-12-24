package minesweeper;

public class EdgeSquare extends SafeSquare {
    private Edge rowEdge;
    private Edge colEdge;
    private Integer adjacentBombs;

    EdgeSquare(int index, Grid grid) {
        super(index, grid);
        rowEdge = determineRowEdge();
        colEdge = determineColEdge();
    }

    EdgeSquare(int row, int col, Grid grid) {
        super(row, col, grid);
        rowEdge = determineRowEdge();
        colEdge = determineColEdge();
    }

    enum Edge {
        TOP, RIGHT, BOTTOM, LEFT, NONE
    }

    private Edge determineRowEdge(){
        if (row == 0) {
            return Edge.TOP;
        }
        if (row == grid.getRows() - 1) {
            return Edge.BOTTOM;
        }
        return Edge.NONE;
    }

    private Edge determineColEdge(){
        if (col == 0) {
            return Edge.LEFT;
        }
        if (col == grid.getCols() - 1) {
            return Edge.RIGHT;
        }
        return Edge.NONE;
    }

    public int countAdjacentBombs() {
        int adjacentBombsCount = 0;
        int startRow = rowEdge == Edge.TOP ? row : row - 1;
        int endRow = rowEdge == Edge.BOTTOM ? row : row + 1;
        int startCol = colEdge == Edge.LEFT ? col : col - 1;
        int endCol = colEdge == Edge.RIGHT ? col : col + 1;
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
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
