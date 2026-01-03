package command;

import utils.SnakeGame;

public class MoveCommand implements Command{
    private final String direction;

    public MoveCommand(String direction){
        this.direction = direction;
    }

    @Override
    public void execute(SnakeGame game) {
//        game.move(direction, game);
    }
}
