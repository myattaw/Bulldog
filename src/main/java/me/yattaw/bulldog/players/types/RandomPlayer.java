package  me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.model.GameStatus;
import me.yattaw.bulldog.players.Player;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * RandomPlayer class: extends Player class.
 * A RandomPlayer stops rolling based on a random chance (50% each turn).
 */
public class RandomPlayer extends Player {

    /** The probability (50%) that the player will continue rolling the die. */
    private static final double CONTINUE_CHANCE = 0.5;

    /**
     * Constructor for creating a default RandomPlayer with the name "Random".
     */
    public RandomPlayer() {
        super("Random");
    }

    /**
     * Constructor for creating a new RandomPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public RandomPlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the RandomPlayer should continue rolling based on random chance.
     * There's a 50% chance to continue after each successful roll (not a six).
     *
     * @param gameStatus The current game status information
     * @return true if random value < CONTINUE_CHANCE and no six was rolled, false otherwise
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        if (gameStatus.getLastRoll() == 6) {
            System.out.printf("   Player %s rolled 6, so player scored 0 for the turn.%n", getName());
            return false;
        }

        System.out.printf("   Player %s rolled %d, cumulative score is %d%n",
                getName(), gameStatus.getLastRoll(), gameStatus.getRoundScore());

        if (Math.random() < CONTINUE_CHANCE) {
            return true;
        } else {
            System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n",
                    getName(), gameStatus.getRoundScore());
            return false;
        }
    }

}