package helper;

import strategy.HumanMovementStrategy;
import utils.Player;
import utils.SnakeGame;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SnakeGameManager {

    private static volatile SnakeGameManager snakeGameManagerInstance;
    private Map<Integer, Player> players = new ConcurrentHashMap<>();

    private SnakeGameManager() {
    }

    public static SnakeGameManager getSnakeGameManagerInstance() {
        if (snakeGameManagerInstance == null) {
            synchronized (SnakeGameManager.class) {
                if (snakeGameManagerInstance == null) {
                    snakeGameManagerInstance = new SnakeGameManager();
                }
            }
        }
        return snakeGameManagerInstance;
    }

    public void initializeSnakeGame(int width, int height, Scanner sc) {

        System.out.print(
                "Enter Player ID (enter 1 to create new, 0 to play as guest): "
        );
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        Player player;

        if (choice == 1) {
            // Create new player
            System.out.print("Enter Player Name: ");
            String name = sc.nextLine();

            int playerId = players.size() + 1;
            player = new Player(playerId, name);
            players.put(playerId, player);

            System.out.println("✅ Player created successfully!");
            System.out.println("🆔 Your Player ID is: " + playerId);

        } else if (choice == 0) {
            // Guest
            player = Player.createGuest(this.players.size());
            System.out.println("Continuing as Guest...");

        } else if (players.containsKey(choice)) {
            // Existing player
            player = players.get(choice);
            System.out.println("Welcome back, " + player.getPlayerName());

        } else {
            // Invalid ID → guest fallback
            System.out.println("❌ Player ID not found. Continuing as Guest...");
            player = Player.createGuest(this.players.size());
        }

        startGame(player, width, height, sc);
    }



    private void startGame(Player player, int width, int height, Scanner sc) {
        System.out.println(
                "Starting game for " + player.getPlayerName() +
                        " on board width " + width + " and height " + height
        );

        SnakeGame snakeGame = new SnakeGame(width, height, new HumanMovementStrategy(), player, true);

        snakeGame.play(sc);

    }
}
