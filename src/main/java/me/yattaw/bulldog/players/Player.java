package me.yattaw.bulldog.players;

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
     * Abstract method that encapsulates one turn for this Player.
     * Subclasses must implement this method to define specific behavior.
     *
     * @return The score earned by the player on this turn, which will be zero if a six was rolled
     */
    public abstract int play();

    /**
     * Simulates rolling a six-sided die and returns a random integer between 1 and 6.
     *
     * @return A random integer between 1 and 6
     */
    protected int rollDie() {
        return (int) (Math.random() * 6) + 1;
    }

}