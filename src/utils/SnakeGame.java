package utils;

import command.CommandInvoker;
import helper.BoardManager;
import helper.FoodManager;
import helper.InputReader;
import strategy.MovementStrategy;

import java.util.Scanner;

public class SnakeGame {

    private final BoardManager boardManager;
    private final Snake snake;
    private final Player player;
    private final FoodManager foodManager;
    private final MovementStrategy movementStrategy;
    private int snakeSize;
    private CommandInvoker commandInvoker = new CommandInvoker();

    public SnakeGame(int width, int height,
                     MovementStrategy movementStrategy,
                     Player player, boolean hasWalls) {

        this.boardManager = new BoardManager(width, height, hasWalls);
        this.snake = new Snake(new Pair(0, 0)); // initialize snake head
        this.player = player;
        this.foodManager = new FoodManager();
        this.movementStrategy = movementStrategy;
        this.snakeSize = 0;

        // ✅ spawn food once at game start
        foodManager.respawn(width, height, snake);
    }

    public void play(Scanner sc) {

        InputReader inputReader = new InputReader();
        Thread inputThread = new Thread(inputReader);
        inputThread.setDaemon(true);
        inputThread.start();

        // print initial board
        printSnakeBoard();

        String direction = "R";

        while (player.isAlive()) {

//            System.out.print("Enter your move U/D/L/R: ");
//            direction = sc.nextLine().trim();

            String input = inputReader.getLatestInput();
            if (input != null && isValidDirection(input.toUpperCase(), direction)) {
                direction = input;
            }

            // ignore empty input
            if (direction.isEmpty()) {
                continue;
            }

            Pair currentHead = snake.getSnakeBody().peekFirst();

            // 1. Calculate new head
            Pair newHead = movementStrategy.getNextPosition(currentHead, direction);

            if(newHead==null){
                System.out.println("Enter valid direction U/D/L/R");
                continue;
            }

            int newHeadRow = newHead.getRow();
            int newHeadCol = newHead.getCol();

            // 2. Wall handling
            if (boardManager.getBoard().isHasWalls()) {
                if (boardManager.isOutOfBounds(newHeadRow, newHeadCol)) {
                    System.out.println("💥 Hit the wall! Game Over.");
                    player.setAlive(false);
                    player.setLostCount(player.getLostCount()+1);
                    break;
                }
            } else {
                newHead = boardManager.wrapPosition(newHeadRow, newHeadCol);
                newHeadRow = newHead.getRow();
                newHeadCol = newHead.getCol();
            }

            Pair currentTail = snake.getSnakeBody().peekLast();

            // 3. Self-collision
            if (boardManager.hasCollision(snake, newHead, currentTail)) {
                System.out.println("💥 Snake bit itself! Game Over.");
                player.setAlive(false);
                player.setLostCount(player.getLostCount()+1);
                break;
            }

            // 4. Food check
            int[] foodPos = foodManager.getCurrentFoodPosition();
            boolean ateFood =
                    foodPos[0] == newHeadRow && foodPos[1] == newHeadCol;


            if (!ateFood) {
                // remove tail if no food eaten
                Pair removedTail = snake.getSnakeBody().pollLast();
                snake.getPositionMap().remove(removedTail);
            } else {
                snakeSize++;

                if (hasWon()) {
                    System.out.println("🎉 YOU WIN! Snake filled the board!");
                    player.setAlive(false);
                    player.setWonCount(player.getWonCount()+1);
                    printSnakeBoard();
                    return;
                }

                foodManager.respawn(
                        boardManager.getBoard().getWidth(),
                        boardManager.getBoard().getHeight(),
                        snake
                );

                System.out.println("🍎 Snake ate food! Size: " + (snakeSize + 1));
            }

            // 5. Move snake
            snake.getSnakeBody().addFirst(newHead);
            snake.getPositionMap().put(newHead, true);

            printSnakeBoard();
            sleep();
        }
        inputReader.stop();
    }

    private boolean isValidDirection(String newDir, String currentDir) {
        return (newDir.equals("U") && !currentDir.equals("D")) ||
                (newDir.equals("D") && !currentDir.equals("U")) ||
                (newDir.equals("L") && !currentDir.equals("R")) ||
                (newDir.equals("R") && !currentDir.equals("L"));
    }


    private void printSnakeBoard() {
        int height = boardManager.getBoard().getHeight();
        int width = boardManager.getBoard().getWidth();
        int[] foodPos = foodManager.getCurrentFoodPosition();

        System.out.println("\nCurrent Board:");

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                Pair cell = new Pair(row, col);

                if (cell.equals(snake.getSnakeBody().peekFirst())) {
                    System.out.print(" H "); // head
                } else if (snake.getPositionMap().containsKey(cell)) {
                    System.out.print(" S "); // body
                } else if (foodPos[0] == row && foodPos[1] == col) {
                    System.out.print(" F "); // food
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }

        System.out.println("Score: " + snakeSize);
    }

    private boolean hasWon() {
        int totalCells =
                boardManager.getBoard().getWidth()
                        * boardManager.getBoard().getHeight();
        return snake.getSnakeBody().size() >= totalCells;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
