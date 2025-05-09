package me.yattaw.bulldog.states;

import me.yattaw.bulldog.core.GameEngine;
import me.yattaw.bulldog.reflection.ReflectionHelper;

import java.util.List;
import java.util.Scanner;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * ConsoleState class: Console-based implementation of the GameState interface for the Bulldog game
 * Handles user input for game initialization and coordinates gameplay through the GameEngine.
 */
public class ConsoleState implements GameState {

    private final GameEngine gameEngine;
    private final Scanner scanner;
    private String matchName;

    public ConsoleState() {
        this.gameEngine = new GameEngine();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void initializeGame() {
        System.out.println("Enter match name: ");
        matchName = scanner.nextLine();

        int playerCount = getPlayerCount();
        gameEngine.createMatch(matchName);

        for (int i = 0; i < playerCount; i++) {
            addPlayer(i + 1);
        }
    }

    private int getPlayerCount() {
        while (true) {
            System.out.println("Enter number of players: ");
            try {
                int count = Integer.parseInt(scanner.nextLine());
                if (count > 0) {
                    return count;
                }
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void addPlayer(int playerNumber) {
        // Get available player types dynamically
        List<String> availableTypes = ReflectionHelper.getAvailablePlayerTypes();

        while (true) {
            // Show dynamic list of available types
            System.out.printf("Enter type for player %d (available: %s): %n",
                    playerNumber,
                    String.join(", ", availableTypes));

            String playerType = scanner.nextLine().toLowerCase();
            if (gameEngine.createPlayer(matchName, playerType)) {
                break;
            }
            System.out.println("Invalid player type. Please try again.");
        }
    }

    @Override
    public void startGame() {
        gameEngine.playUntilWinner(matchName);
        displayResults();
    }

    @Override
    public void playTurn() {
        // Handled within playUntilWinner in BulldogApplication
    }

    @Override
    public void displayResults() {
        // Results are displayed during gameplay in playUntilWinner
    }

}
