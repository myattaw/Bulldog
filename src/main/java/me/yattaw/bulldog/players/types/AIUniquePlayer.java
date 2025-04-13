package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.model.GameStatus;
import me.yattaw.bulldog.players.Player;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * AIUniquePlayer class: extends Player class.
 * A AIUniquePlayer uses a dynamic threshold to decide when to stop rolling.
 */
public class AIUniquePlayer extends Player {

    /** The base threshold value for stopping rolls. */
    private static final int BASE_THRESHOLD = 20;

    /** The minimum threshold value, ensuring it never goes below this value. */
    private static final int MIN_THRESHOLD = 10;

    /** The divisor used to adjust the threshold based on the player's overall score. */
    private static final int SCORE_DIVISOR = 10;

    /**
     * Constructor for creating a default AIUniquePlayer with the name "Unique".
     */
    public AIUniquePlayer() {
        super("Unique");
    }

    /**
     * Constructor for creating a new AIUniquePlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public AIUniquePlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the AI player should continue rolling based on their dynamic threshold strategy.
     * The threshold decreases as the player's overall score increases.
     *
     * @param gameStatus The current game status information
     * @return true if should continue rolling, false otherwise
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        if (gameStatus.getLastRoll() == 6) {
            System.out.println("   Player " + getName() + " rolled " + gameStatus.getLastRoll() +
                    " and scored 0 for the turn.");
            return false;
        }

        System.out.println("   Player " + getName() + " rolled " + gameStatus.getLastRoll() +
                " and has a cumulative score of " + gameStatus.getRoundScore() + " for the turn.");

        int threshold = calculateThreshold();
        if (gameStatus.getRoundScore() >= threshold) {
            System.out.println("   Player " + getName() + " chose to end the turn with a score of " +
                    gameStatus.getRoundScore() + ".");
            return false;
        }

        System.out.println("   Player " + getName() + " chose to continue rolling.");
        return true;
    }

    /**
     * Calculates the dynamic threshold for deciding when to stop rolling.
     * The threshold decreases as the player's overall score increases but never goes below the minimum threshold.
     *
     * @return The calculated threshold value
     */
    private int calculateThreshold() {
        return Math.max(MIN_THRESHOLD, BASE_THRESHOLD - (getScore() / SCORE_DIVISOR));
    }

}