package utils;

public class Board {
    private int width;
    private int height;
    private boolean hasWalls;

    public Board(int width, int height, boolean hasWalls) {
        this.width = width;
        this.height = height;
        this.hasWalls = hasWalls;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHasWalls() {
        return hasWalls;
    }
}
