package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

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
     * Takes turns for this Player, allowing the user to decide when to stop rolling.
     * The user can type 'stop' to end their turn or press Enter to continue rolling.
     * If a six is rolled, the player scores 0 for the turn. Otherwise, the player
     * accumulates the sum of the rolls until they choose to stop.
     *
     * @return The score earned by the player on this turn, which will be zero if a six was rolled
     */
    @Override
    public int play() {
        int roundScore = 0;

        while (true) {
            System.out.println("Type 'stop' to end turn or press Enter to continue:");
            String input = SCANNER.nextLine().trim();

            if (input.equalsIgnoreCase("stop")) {
                System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n", getName(), roundScore);
                return roundScore;
            }

            int roll = rollDie();
            if (roll == 6) {
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        }
    }

}