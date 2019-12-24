package minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MineSweeper {

    MineSweeper() {

    }

    public final void run(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer bombNum = null;
        while (bombNum == null || bombNum < 0 || bombNum > 80) {
            System.out.println("How many mines do you want on the field? (1 - 80)");
            bombNum = scanner.nextInt();
        }
        Set<Integer> bombIndices = new HashSet<>();
        Random random = new Random();
        while (bombIndices.size() < bombNum) {
            bombIndices.add(random.nextInt(81));
        }
        Grid grid = new Grid(9);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                GameSquare square;
                if (bombIndices.contains(i * 9 + j)) {
                    square = new BombSquare(i, j, grid);
                } else if (i == 0 || i == 8 || j == 0 || j == 8) {
                    square = new EdgeSquare(i, j, grid);
                } else {
                    square = new SafeSquare(i, j, grid);
                }
                grid.setSquareValue(i, j, square);
            }
        }
        grid.printGrid();
    }
}
