package minesweeper;

class GameSquare {
    private boolean hasBomb;

    GameSquare(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    boolean getHasBomb() {
        return hasBomb;
    }
}
