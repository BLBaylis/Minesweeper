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
        while (bombNum == null || bombNum < 0 || bombNum > 80) {
            System.out.println("How many mines do you want on the field? (1 - 80)");
            bombNum = scanner.nextInt();
        }
        Set<Integer> bombIndices = new HashSet<>();
        Random random = new Random();
        while (bombIndices.size() < bombNum) {
            bombIndices.add(random.nextInt(81));
        }
        Grid grid = new Grid(9, 9);
        grid.populateGrid(bombIndices);
        grid.printGrid();
    }
}
