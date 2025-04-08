package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.data.Dice;
import me.yattaw.bulldog.data.RandomDice;
import me.yattaw.bulldog.players.Player;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * SevenPlayer class: extends Player class.
 * A SevenPlayer rolls until their score reaches seven or greater.
 */
public class SevenPlayer extends Player {

    /** The target score (7) that the player aims to reach or exceed. */
    private static final int TARGET_SCORE = 7;

    private RandomDice dice = Dice.DEFAULT_DIE;

    /**
     * Constructor for creating a default FifteenPlayer with the name "Fifteen".
     */
    public SevenPlayer() {
        super("Seven");
    }

    /**
     * Constructor for creating a new FifteenPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public SevenPlayer(String name) {
        super(name);
    }

    /**
     * Takes turns rolling the die until the player's cumulative score reaches or exceeds the target score (15).
     * If a six is rolled, the player scores 0 for the turn. Otherwise, the player accumulates the sum of the rolls
     * until the target score is reached.
     *
     * @return The score earned by the player on this turn, which will be zero if a six was rolled
     */
    @Override
    public int play() {
        int roundScore = 0;

        while (roundScore < TARGET_SCORE) {
            int roll = rollDie();
            if (roll == 6) {
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        }

        System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n", getName(), roundScore);
        return roundScore;
    }

    public void setDice(RandomDice dice) {
        this.dice = dice;
    }

    @Override
    public int rollDie() {
        return dice.roll();
    }

}