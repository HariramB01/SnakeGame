import helper.SnakeGameManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Snake Game!!!");
        Scanner sc = new Scanner(System.in);
        SnakeGameManager snakeGameManagerInstance = SnakeGameManager.getSnakeGameManagerInstance();
        snakeGameManagerInstance.run(sc);
        sc.close();
        System.out.println("Thanks for playing!");
    }
}
