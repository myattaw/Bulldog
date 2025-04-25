package me.yattaw.bulldog.core.players;

import me.yattaw.bulldog.core.data.Dice;
import me.yattaw.bulldog.core.model.GameStatus;

/**
 * David Levine
 * Login ID: david.b.levine@maine.edu
 * COS 497, Summer 2024
 * Programming Assignment 6
 * Abstract Player class: holds generic info about a player of the game Bulldog.
 * See Kettering University, CS-101, Prog 6.
 */
public abstract class Player {

    /** The name of the Player. */
    private String name;

    /** The score earned by this Player during the game. */
    private int score;

    /**
     * Constructor for creating a new Player object.
     *
     * @param name The name of the Player being created
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Returns the name of this Player.
     *
     * @return The name of this Player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current score of this Player.
     *
     * @return The current score of this Player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the current score of this Player.
     *
     * @param score The new value of the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Encapsulates one turn for this Player.
     * The common logic is moved here from concrete subclasses.
     *
     * @return The score earned by the player on this turn
     */
    public int play() {
        GameStatus gameStatus = new GameStatus();

        while (!gameStatus.isTurnOver()) {
            int roll = rollDie();
            gameStatus.setLastRoll(roll);

            if (roll == 6) {
                gameStatus.setRoundScore(0);
                gameStatus.setTurnOver(true);
            } else {
                gameStatus.setRoundScore(gameStatus.getRoundScore() + roll);
                if (!continueRolling(gameStatus)) {
                    gameStatus.setTurnOver(true);
                }
            }
        }

        this.score += gameStatus.getRoundScore();
        return gameStatus.getRoundScore();
    }

    /**
     * Abstract method that determines whether the player wants to continue rolling.
     * Subclasses must implement this to provide their specific decision logic.
     *
     * @param gameStatus The current game status information
     * @return true if the player wants to continue rolling, false otherwise
     */
    public abstract boolean continueRolling(GameStatus gameStatus);

    /**
     * Simulates rolling a six-sided die and returns a random integer between 1 and 6.
     *
     * @return A random integer between 1 and 6
     */
    public int rollDie() {
        return Dice.DEFAULT_DIE.roll();
    }

}