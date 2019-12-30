package minesweeper;

class GameSquare {
    private boolean hasBomb;
    private boolean isHidden;
    private int adjacentBombCount;

    GameSquare(boolean hasBomb) {
        this.hasBomb = hasBomb;
        this.isHidden = true;
    }

    public int getAdjacentBombCount() {
        return adjacentBombCount;
    }

    public void setAdjacentBombCount(int adjacentBombCount) {
        this.adjacentBombCount = adjacentBombCount;
    }

    boolean getHasBomb() {
        return hasBomb;
    }

    boolean getIsHidden() {
        return isHidden;
    }

    void reveal() {
        if (!hasBomb) {
            isHidden = false;
        }
    }
}