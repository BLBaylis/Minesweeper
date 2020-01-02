package minesweeper;

import java.util.*;

class MineSweeper {
    private Grid grid;
    private boolean firstSquareRevealed = false;
    private boolean gameFinished = false;
    private Set<Integer> bombIndices = new HashSet<>();
    private Integer bombNum = null;

    MineSweeper(Grid grid) {
        this.grid = grid;
    }

    private void determineBombLocations(int invalidBombIndex, int bombNum) {
        Random random = new Random();
        while (bombIndices.size() < bombNum) {
            int bombIndex = random.nextInt(81);
            if (bombIndex != invalidBombIndex) {
                bombIndices.add(bombIndex);
            }
        }
    }

    private void checkWin() {
        boolean allBombsMarked = true;
        boolean allNonBombsRevealed = true;
        for (int squareIndex = 0; squareIndex < 81; squareIndex++) {
            GameSquare square = grid.getSquareAtIndex(squareIndex);
            boolean squareIsHidden = !square.isVisible();
            if (squareIsHidden) {
                allNonBombsRevealed = false;
            }
            if (bombIndices.contains(squareIndex) && !square.isMarked()) {
                allBombsMarked = false;
            }
        }
        if (allBombsMarked && allNonBombsRevealed) {
            gameFinished = true;
            System.out.println("Congratulations! You found all mines!");
        }
    }

    final void run() {
        Scanner scanner = new Scanner(System.in);
        while (bombNum == null || bombNum < 0 || bombNum > 80) {
            System.out.println("How many mines do you want on the field? (1 - 80)");
            bombNum = scanner.nextInt();
        }
        grid.printGrid(false);
        while (!firstSquareRevealed || !gameFinished) {
            System.out.println("Set/delete mine marks (x and y coordinates): ");
            int col = scanner.nextInt() - 1;
            int row = scanner.nextInt() - 1;
            int index = row * 9 + col;
            String moveType = scanner.next();
            GameSquare selectedSquare = grid.getSquareAtIndex(index);
            if (selectedSquare.isVisible()) {
                System.out.println("Please select a square that isn't hidden");
                continue;
            }
            if ("mine".equals(moveType)) {
                selectedSquare.toggleMark();
            } else if ("free".equals(moveType)) {
                if (!firstSquareRevealed) {
                    determineBombLocations(index, bombNum);
                    setBombs();
                    firstSquareRevealed = true;
                    setAllAdjacentBombCounts();
                }
                if (selectedSquare.isBomb()) {
                    gameFinished = true;
                    System.out.println("You stepped on a mine and failed!");
                    grid.printGrid(gameFinished);
                    break;
                }
                selectedSquare.reveal();
                revealAllSafeSquares(index);
            } else {
                System.out.println("Incorrect syntax");
                continue;
            }
            checkWin();
            grid.printGrid(gameFinished);
        }
    }

    private void revealAllSafeSquares(int index) {
        Deque<Integer> revealedIndices = new ArrayDeque<>();
        addAdjacentSquaresToQueue(index, revealedIndices);
        while (!revealedIndices.isEmpty()) {
            int squareIndex = revealedIndices.removeFirst();
            GameSquare square = grid.getSquareAtIndex(squareIndex);
            square.reveal();
            if (square.getAdjacentBombCount() == 0) {
                addAdjacentSquaresToQueue(squareIndex, revealedIndices);
            }
        }
    }

    private void addAdjacentSquaresToQueue(int index, Deque<Integer> revealedIndices) {
        Set<Integer> adjacentSquareIndices = grid.getAdjacentSquares(index);
        for (int adjacentIndex : adjacentSquareIndices) {
            GameSquare square = grid.getSquareAtIndex(adjacentIndex);
            if (!square.isVisible()) {
                revealedIndices.addLast(adjacentIndex);
            }
        }
    }

    private void setBombs() {
        for (int bombIndex : bombIndices) {
            GameSquare square = grid.getSquareAtIndex(bombIndex);
            square.setBomb();
        }
    }

    private void setAllAdjacentBombCounts() {
        for (int squareIndex = 0; squareIndex < 81; squareIndex++) {
            GameSquare square = grid.getSquareAtIndex(squareIndex);
            Set<Integer> adjacentSquareIndices = grid.getAdjacentSquares(squareIndex);
            int adjacentBombCount = 0;
            for (int adjacentIndex : adjacentSquareIndices) {
                GameSquare adjacentSquare = grid.getSquareAtIndex(adjacentIndex);
                if (adjacentSquare.isBomb()) {
                    adjacentBombCount++;
                }
            }
            square.setAdjacentBombCount(adjacentBombCount);
        }
    }

}