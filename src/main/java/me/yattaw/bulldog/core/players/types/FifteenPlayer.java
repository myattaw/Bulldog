package me.yattaw.bulldog.core.players.types;

import me.yattaw.bulldog.core.model.GameStatus;
import me.yattaw.bulldog.core.players.Player;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * FifteenPlayer class: extends Player class.
 * A FifteenPlayer rolls until their score reaches fifteen or greater.
 */
public class FifteenPlayer extends Player {

    /** The target score (15) that the player aims to reach or exceed. */
    private static final int TARGET_SCORE = 15;

    /**
     * Constructor for creating a default FifteenPlayer with the name "Fifteen".
     */
    public FifteenPlayer() {
        super("Fifteen");
    }

    /**
     * Constructor for creating a new FifteenPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public FifteenPlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the FifteenPlayer should continue rolling based on their target score strategy.
     * Continues rolling until reaching or exceeding TARGET_SCORE (15), unless a six is rolled.
     *
     * @param gameStatus The current game status information
     * @return true if should continue rolling (score < 15 and no six rolled), false otherwise
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        if (gameStatus.getLastRoll() == 6) {
            System.out.printf("   Player %s rolled 6, so player scored 0 for the turn.%n", getName());
            return false;
        }

        if (gameStatus.getRoundScore() < TARGET_SCORE) {
            System.out.printf("   Player %s rolled %d, cumulative score is %d%n",
                    getName(), gameStatus.getLastRoll(), gameStatus.getRoundScore());
            return true;
        } else {
            System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n",
                    getName(), gameStatus.getRoundScore());
            return false;
        }
    }

}