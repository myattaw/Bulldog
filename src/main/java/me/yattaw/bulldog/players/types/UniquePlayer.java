package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.model.GameStatus;
import me.yattaw.bulldog.players.Player;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * UniquePlayer class: extends Player class.
 * A UniquePlayer takes turns exponentially each round, doubling the number of rolls
 * until a six is rolled, which resets the exponential counter.
 */
public class UniquePlayer extends Player {

    /** Tracks the exponential number of rolls for each turn. */
    private int exponential;

    /** Tracks the number of rolls made in the current turn. */
    private int rollsThisTurn;

    /**
     * Constructor for creating a default UniquePlayer with the name "Exponential".
     */
    public UniquePlayer() {
        super("Exponential");
        this.exponential = 0;
        this.rollsThisTurn = 0;
    }

    /**
     * Constructor for creating a new UniquePlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public UniquePlayer(String name) {
        super(name);
        this.exponential = 0;
        this.rollsThisTurn = 0;
    }

    /**
     * Determines whether the UniquePlayer should continue rolling based on their exponential strategy.
     * Rolls 2^exponential times each round, unless a six is rolled.
     *
     * @param gameStatus The current game status information
     * @return true if should continue rolling (haven't reached 2^exponential rolls yet), false otherwise
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        rollsThisTurn++;
        int targetRolls = (int) Math.pow(2, exponential);

        if (gameStatus.getLastRoll() == 6) {
            exponential = 0;
            rollsThisTurn = 0;
            System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n",
                    getName(), gameStatus.getLastRoll());
            return false;
        }

        System.out.printf("   Player %s rolled %d, cumulative score is %d%n",
                getName(), gameStatus.getLastRoll(), gameStatus.getRoundScore());

        if (rollsThisTurn < targetRolls) {
            return true;
        } else {
            exponential++;
            rollsThisTurn = 0;
            return false;
        }
    }

}