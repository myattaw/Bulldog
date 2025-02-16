package me.yattaw.bulldog.players.types;

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

    /**
     * Constructor for creating a default UniquePlayer with the name "Exponential".
     */
    public UniquePlayer() {
        super("Exponential");
    }

    /**
     * Constructor for creating a new UniquePlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public UniquePlayer(String name) {
        super(name);
    }

    /**
     * Takes turns exponentially each round, doubling the number of rolls until a six is rolled.
     * If a six is rolled, the exponential counter is reset, and the player scores 0 for the turn.
     * Otherwise, the player accumulates the sum of the rolls for the turn.
     *
     * @return The score earned by the player on this turn, which will be zero if a six was rolled
     */
    @Override
    public int play() {
        int roundScore = 0;
        for (int i = 0; i < Math.pow(2, exponential); i++) {
            int roll = rollDie();
            if (roll == 6) {
                exponential = 0;
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        }
        exponential++;
        return roundScore;
    }

}