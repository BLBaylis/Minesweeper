package minesweeper;

class GameSquare {
    private boolean bomb;
    private boolean visible;
    private boolean marked;
    private int adjacentBombCount;

    GameSquare() {
        this.bomb = false;
        this.visible = false;
        this.marked = false;
    }

    boolean isBomb() {
        return bomb;
    }

    boolean isMarked() {
        return marked;
    }

    boolean isVisible() {
        return visible;
    }

    int getAdjacentBombCount() {
        return adjacentBombCount;
    }

    void setAdjacentBombCount(int adjacentBombCount) {
        this.adjacentBombCount = adjacentBombCount;
    }

    void setBomb() {
        this.bomb = true;
    }

    void toggleMark() {
        marked = !marked;
    }

    void reveal() {
        if (!bomb) {
            visible = true;
        }
    }
}