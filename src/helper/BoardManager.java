package helper;

import utils.Board;
import utils.Pair;
import utils.Snake;

public class BoardManager {

    private Board board;

    public BoardManager(int width, int height, boolean hasWalls) {
        this.board = new Board(width, height, hasWalls);
    }


    public boolean isOutOfBounds(int newHeadRow, int newHeadColumn) {
        return newHeadRow < 0 || newHeadRow >= this.board.getHeight()
                || newHeadColumn < 0 || newHeadColumn >= this.board.getWidth();
    }

    public Board getBoard() {
        return this.board;
    }

    //    checking whether currentHead (updated head) collides with currentTail
    public boolean hasCollision(Snake snake, Pair newHead, Pair currentTail) {
        return snake.getSnakeBody().contains(newHead)
                && !(newHead.getRow() == currentTail.getRow()
                && newHead.getCol() == currentTail.getCol());
    }

    // 3*3
    // (0,2) right direction -> (0,3) -> (1,3)
    // (0,2) up direction -> (1,2) -> (2,2)
    // (0,0) left direction -> (-1,0) -> (2,0)
    // (2,0) down direction -> (3,0) -> (0,0)
    public Pair wrapPosition(int row, int col) {
        int height = this.board.getHeight();
        int width = this.board.getWidth();
        // Wrap around height
        if (row < 0) {
            row = height - 1;
        } else if (row >= height) {
            row = 0;
        }
        // Wrap around width
        if (col < 0) {
            col = width - 1;
        } else if (col >= width) {
            col = 0;
        }
        return new Pair(row, col);
    }

    public boolean foodCollidesWithSnake(int[] currentFoodPosition, Snake snake) {
        return snake.getPositionMap().containsKey(new Pair(currentFoodPosition[0], currentFoodPosition[1]));
    }
}
