import helper.SnakeGameManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SnakeGameManager snakeGameManager =
                SnakeGameManager.getSnakeGameManagerInstance();

        System.out.println("Welcome to Snake Game!!!");
        Scanner sc = new Scanner(System.in);

        boolean snakeGame = true;

        while (snakeGame) {
            System.out.print("Initialize your board width: ");
            int width = sc.nextInt();

            System.out.print("Initialize your board height: ");
            int height = sc.nextInt();

            snakeGameManager.initializeSnakeGame(width, height, sc);

            System.out.print("Do you want to play again? (y/n) ");
            String choice = sc.next();

            snakeGame = choice.equalsIgnoreCase("y");
        }

        sc.close();
        System.out.println("Thanks for playing!");
    }
}
