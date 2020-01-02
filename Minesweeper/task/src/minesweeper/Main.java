package minesweeper;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(9, 9);
        grid.populateGrid();
        MineSweeper game = new MineSweeper(grid);
        game.run();
    }
}
