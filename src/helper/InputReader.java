package helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputReader implements Runnable {

    private volatile String latestInput = "R";
    private volatile boolean running = true;

    @Override
    public void run() {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        while (running) {
            try {
                if (reader.ready()) {
                    String input = reader.readLine().trim().toUpperCase();
                    if (input.matches("[UDLR]")) {
                        latestInput = input;
                    }
                }
            } catch (IOException ignored) {}
        }
    }

    public String getLatestInput() {
        return latestInput;
    }

    public void stop() {
        running = false;
        System.out.print("Do you want to play again? (y = yes, n = no): ");
    }
}
