package me.yattaw.bulldog.core.players.types;

import me.yattaw.bulldog.core.model.GameStatus;
import me.yattaw.bulldog.core.players.Player;

import java.util.Scanner;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * HumanPlayer class: extends Player class.
 * A HumanPlayer allows the user to decide when to stop rolling.
 */
public class HumanPlayer extends Player {

    /** Scanner object to read user input from the console. */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor for creating a default HumanPlayer with the name "Human".
     */
    public HumanPlayer() {
        super("Human");
    }

    /**
     * Constructor for creating a new HumanPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the human player wants to continue rolling.
     * Prompts the user for input after each roll (unless a six was rolled).
     *
     * @param gameStatus The current game status information
     * @return true if player wants to continue rolling, false otherwise
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        if (gameStatus.getLastRoll() == 6) {
            System.out.printf("   Player %s rolled 6, so player scored 0 for the turn.%n", getName());
            return false;
        }

        System.out.printf("   Player %s rolled %d, cumulative score is %d%n",
                getName(), gameStatus.getLastRoll(), gameStatus.getRoundScore());

        System.out.println("Type 'stop' to end turn or press Enter to continue:");
        String input = SCANNER.nextLine().trim();

        if (input.equalsIgnoreCase("stop")) {
            System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n",
                    getName(), gameStatus.getRoundScore());
            return false;
        }

        return true;
    }

}