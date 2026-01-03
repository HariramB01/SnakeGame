package helper;

import java.util.Scanner;

public class InputReader implements Runnable {

    private final Scanner scanner;
    private volatile String latestInput = "R";
    private volatile boolean running = true;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (running) {
            if (!scanner.hasNextLine()) break;

            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("U") || input.equals("D")
                    || input.equals("L") || input.equals("R")) {
                latestInput = input;
            }
        }
    }

    public String getLatestInput() {
        return latestInput;
    }

    public void stop() {
        running = false;
    }
}
