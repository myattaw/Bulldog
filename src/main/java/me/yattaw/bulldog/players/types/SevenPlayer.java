package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.data.Dice;
import me.yattaw.bulldog.data.RandomDice;
import me.yattaw.bulldog.model.GameStatus;
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
     * Constructor for creating a default SevenPlayer with the name "Seven".
     */
    public SevenPlayer() {
        super("Seven");
    }

    /**
     * Constructor for creating a new SevenPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public SevenPlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the SevenPlayer should continue rolling based on their target score strategy.
     * Continues rolling until reaching or exceeding TARGET_SCORE (7), unless a six is rolled.
     *
     * @param gameStatus The current game status information
     * @return true if should continue rolling (score < 7 and no six rolled), false otherwise
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

    public void setDice(RandomDice dice) {
        this.dice = dice;
    }

    @Override
    public int rollDie() {
        return dice.roll();
    }

}