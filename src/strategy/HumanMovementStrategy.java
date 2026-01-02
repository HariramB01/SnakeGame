package strategy;

import utils.Pair;

public class HumanMovementStrategy implements MovementStrategy {

    @Override
    public Pair getNextPosition(Pair currentHead, String direction) {

        int row = currentHead.getRow();
        int col = currentHead.getCol();

        if (direction == null || direction.isEmpty()) {
            return currentHead;
        }

        switch (direction.toUpperCase()) {
            case "U":
                row--;
                break;
            case "D":
                row++;
                break;
            case "L":
                col--;
                break;
            case "R":
                col++;
                break;
            default:
                // Invalid input → no movement
                return currentHead;
        }

        return new Pair(row, col);
    }
}
