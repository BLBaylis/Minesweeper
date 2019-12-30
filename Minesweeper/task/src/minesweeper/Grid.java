package minesweeper;

import java.util.*;

class Grid {
    private GameSquare[][] grid;
    private int rows;
    private int cols;
    private Set<Integer> markedIndices = new HashSet<>();
    private Set<Integer> bombIndices = new HashSet<>();
    private Deque<Integer> revealedIndices = new ArrayDeque<>();
    private boolean isGameLost = false;
    private boolean populated = false;
    private int bombNum;

    Grid(int rows, int cols) {
        grid = new GameSquare[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    static int[] convertIndexToRowsAndCols(int index) {
        return new int[]{index / 9, index % 9};
    }

    boolean isGameWon() {
        return bombIndices.equals(markedIndices) && populated;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    void populateGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameSquare square = new GameSquare(bombIndices.contains(i * 9 + j));
                grid[i][j] = square;
            }
        }
    }

    void setAllAdjacentBombCounts() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                GameSquare square = grid[i][j];
                Set<Integer> adjacentSquareIndices = getAdjacentSquares(i, j);
                int adjacentBombCount = 0;
                for (int index : adjacentSquareIndices) {
                    int[] indices = convertIndexToRowsAndCols(index);
                    boolean hasBomb = grid[indices[0]][indices[1]].getHasBomb();
                    if (hasBomb) {
                        adjacentBombCount++;
                    }
                }
                square.setAdjacentBombCount(adjacentBombCount);
            }
        }
    }

    boolean getIsGameLost() {
        return isGameLost;
    }

    Set<Integer> getAdjacentSquares(int row, int col) {
        Set<Integer> adjacentSquareIndices = new HashSet<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i > 8 || j < 0 || j > 8 || (i == row && j == col)) {
                    continue;
                }
                adjacentSquareIndices.add(i * 9 + j);
            }
        }
        return adjacentSquareIndices;
    }

    void generateGrid(int invalidBombIndex) {
        Random random = new Random();
        while (bombIndices.size() < bombNum) {
            int bombIndex = random.nextInt(81);
            if (bombIndex != invalidBombIndex) {
                bombIndices.add(bombIndex);
            }
        }
        populateGrid();
        populated = true;
        setAllAdjacentBombCounts();
    }

    void markSquare(int row, int col) throws IllegalArgumentException {
        GameSquare square = grid[row - 1][col - 1];
        int index = (row - 1) * 9 + (col - 1);
        if (populated && !square.getHasBomb() && !square.getIsHidden() && square.getAdjacentBombCount() != 0) {
            throw new IllegalArgumentException("Can't mark a number square");
        }
        if (!markedIndices.contains(index)) {
            markedIndices.add(index);
        } else {
            markedIndices.remove(index);
        }
    }

    void revealMove(int row, int col) {
        int index = (row - 1) * 9 + (col - 1);
        if (!populated) {
            generateGrid(index);
        }
        if (revealedIndices.contains(index)) {
            System.out.println("Already selected");
            return;
        }
        //add first square to be revealed
        GameSquare selectedSquare = grid[row - 1][col - 1];
        if (selectedSquare.getHasBomb()) {
            isGameLost = true;
            return;
        }
        selectedSquare.reveal();
        //add all of first numbers adjacent hidden squares
        addAdjacentSquaresToRevealed(row - 1, col - 1);
        while (!revealedIndices.isEmpty()) {
            int squareIndex = revealedIndices.removeFirst();
            int[] indices = convertIndexToRowsAndCols(squareIndex);
            GameSquare square = grid[indices[0]][indices[1]];
            square.reveal();
            if (square.getAdjacentBombCount() == 0) {
                //if it has no bombs around it, add adjacents to to the queue
                addAdjacentSquaresToRevealed(indices[0], indices[1]);
            }
        }
    }

    void addAdjacentSquaresToRevealed(int row, int col) {
        Set<Integer> adjacentSquareIndices = getAdjacentSquares(row, col);
        for (int adjacentIndex : adjacentSquareIndices) {
            int[] indices = convertIndexToRowsAndCols(adjacentIndex);
            GameSquare square = grid[indices[0]][indices[1]];
            if (square.getIsHidden()) {
                revealedIndices.addLast(adjacentIndex);
            }
        }
    }

    void printInitialGrid() {
        System.out.println(" │123456789│");
        System.out.println("—│—————————│");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1 +"│");
            for (int j = 0; j < cols; j++) {
                boolean isMarkedSquare = markedIndices.contains(i * 9 + j);
                if (isMarkedSquare) {
                    System.out.print("*");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("│");
        }
        System.out.println("—│—————————│");
    }

    void printHiddenGrid() {
        System.out.println(" │123456789│");
        System.out.println("—│—————————│");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1 +"│");
            for (int j = 0; j < cols; j++) {
                GameSquare square = grid[i][j];
                int gridIndex = i * 9 + j;
                boolean isMarkedSquare = markedIndices.contains(gridIndex);
                boolean isHiddenSquare = square.getIsHidden();
                boolean isBombSquare = square.getHasBomb();
                if (isBombSquare && isGameLost) {
                    System.out.print("X");
                } else if (isMarkedSquare && isHiddenSquare) {
                    System.out.print("*");
                } else if (isHiddenSquare) {
                    System.out.print(".");
                } else {
                    int adjacentBombs = square.getAdjacentBombCount();
                    System.out.print(adjacentBombs == 0 ? "/" : adjacentBombs);
                }
            }
            System.out.println("│");
        }
        System.out.println("—│—————————│");

    }
}