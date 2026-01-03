package command;

import utils.SnakeGame;

import java.util.LinkedList;
import java.util.Queue;

public class CommandInvoker {

    private Queue<Command> commandQueue = new LinkedList<>();

    public void addCommand(Command command){
        commandQueue.offer(command);
    }

    public void execute(SnakeGame snakeGame){
        while(!commandQueue.isEmpty()){
            Command command = commandQueue.poll();
            command.execute(snakeGame);
        }
    }

}
