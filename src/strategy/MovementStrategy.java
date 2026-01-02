package strategy;

import utils.Pair;

public interface MovementStrategy {
    Pair getNextPosition(Pair currentHead, String direction);
}
