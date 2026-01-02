package utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Snake {

    private final Deque<Pair> snakeBody;
    private final Map<Pair, Boolean> positionMap;

    public Snake(Pair startPosition) {
        this.snakeBody = new ArrayDeque<>();
        this.positionMap = new HashMap<>();

        // Initialize snake with head
        snakeBody.addFirst(startPosition);
        positionMap.put(startPosition, true);
    }

    public Deque<Pair> getSnakeBody() {
        return snakeBody;
    }

    public Map<Pair, Boolean> getPositionMap() {
        return positionMap;
    }
}
