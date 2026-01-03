package helper;

import strategy.HumanMovementStrategy;
import utils.Player;
import utils.SnakeGame;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SnakeGameManager {

    private static volatile SnakeGameManager instance;
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();

    private SnakeGameManager() {}

    public static SnakeGameManager getSnakeGameManagerInstance() {
        if (instance == null) {
            synchronized (SnakeGameManager.class) {
                if (instance == null) {
                    instance = new SnakeGameManager();
                }
            }
        }
        return instance;
    }

    public void run(Scanner sc) {

        boolean playAgain = true;

        while (playAgain) {

            System.out.print("Initialize your board width: ");
            int width = Integer.parseInt(sc.nextLine());

            System.out.print("Initialize your board height: ");
            int height = Integer.parseInt(sc.nextLine());

            Player player = resolvePlayer(sc);

            SnakeGame game = new SnakeGame(
                    width, height,
                    new HumanMovementStrategy(),
                    player,
                    true
            );

            game.play(sc);

            String choice;
            while (true) {
                choice = sc.nextLine().trim().toLowerCase();
                if (choice.equals("y") || choice.equals("n")) {
                    break;
                }
            }

            playAgain = choice.equals("y");
        }
    }

    private Player resolvePlayer(Scanner sc) {

        System.out.println("\nChoose an option:");
        System.out.println("1 → Create new player");
        System.out.println("2 → Login with existing Player ID");
        System.out.println("0 → Play as Guest");
        System.out.print("Your choice: ");

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {

            case 1 -> {
                return createNewPlayer(sc);
            }

            case 2 -> {
                if (players.isEmpty()) {
                    System.out.println("⚠️ No players exist yet.");
                    return askCreateOrGuest(sc);
                }

                System.out.print("Enter Player ID: ");
                int id = Integer.parseInt(sc.nextLine());

                if (players.containsKey(id)) {
                    System.out.println("Welcome back " +
                            players.get(id).getPlayerName());
                    return players.get(id);
                }

                System.out.println("❌ Player ID not found.");
                return askCreateOrGuest(sc);
            }

            case 0 -> {
                System.out.println("Playing as Guest");
                return Player.createGuest(players.size() + 1);
            }

            default -> {
                System.out.println("❌ Invalid choice.");
                return askCreateOrGuest(sc);
            }
        }
    }
    private Player createNewPlayer(Scanner sc) {
        System.out.print("Enter Player Name: ");
        String name = sc.nextLine();

        int id = players.size() + 1;
        Player p = new Player(id, name);
        players.put(id, p);

        System.out.println("✅ Player created!");
        System.out.println("🆔 Your Player ID is: " + id);
        return p;
    }

    private Player askCreateOrGuest(Scanner sc) {
        System.out.println("Choose an option:");
        System.out.println("1 → Create new player");
        System.out.println("0 → Continue as Guest");
        System.out.print("Your choice: ");

        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {
            return createNewPlayer(sc);
        }

        System.out.println("Continuing as Guest");
        return Player.createGuest(players.size() + 1);
    }

}
