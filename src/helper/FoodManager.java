package helper;

import utils.Pair;
import utils.Snake;

import java.util.Random;

public class FoodManager {

    private int row;
    private int col;

    private final Random random = new Random();

    /** Generate food at a random EMPTY cell */
    public void respawn(int boardWidth, int boardHeight, Snake snake) {

        Pair foodPos;
        do {
            row = random.nextInt(boardHeight); // rows
            col = random.nextInt(boardWidth);  // cols
            foodPos = new Pair(row, col);
        } while (snake.getPositionMap().containsKey(foodPos));
    }

    public int[] getCurrentFoodPosition() {
        return new int[]{row, col};
    }
}
