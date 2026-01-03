package strategy;

import utils.Pair;

public class HumanMovementStrategy implements MovementStrategy {

    @Override
    public Pair getNextPosition(Pair currentHead, String direction) {

        int row = currentHead.getRow();
        int col = currentHead.getCol();

        switch (direction.toUpperCase()) {
            case "U": row -= 1; break;
            case "D": row += 1; break;
            case "L": col -= 1; break;
            case "R": col += 1; break;
            default:
                return null;
        }

        return new Pair(row, col);
    }
}
