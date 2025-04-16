package me.yattaw.bulldog.model;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * GameStatus class: Manages game state, including round score, turn status, and last roll data.
 */
public class GameStatus {

    private int roundScore;
    private boolean turnOver;
    private int lastRoll;

    /**
     * Constructs a new GameStatus object with initial values.
     * Initializes roundScore to 0, turnOver to false, and lastRoll to 0.
     */
    public GameStatus() {
        this.roundScore = 0;
        this.turnOver = false;
        this.lastRoll = 0;
    }

    /**
     * Returns the current round score.
     *
     * @return the round score
     */
    public int getRoundScore() {
        return roundScore;
    }

    /**
     * Sets the round score to the specified value.
     *
     * @param roundScore the new round score
     */
    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    /**
     * Checks if the turn is over.
     *
     * @return true if the turn is over, false otherwise
     */
    public boolean isTurnOver() {
        return turnOver;
    }

    /**
     * Sets whether the turn is over.
     *
     * @param turnOver true to indicate the turn is over, false otherwise
     */
    public void setTurnOver(boolean turnOver) {
        this.turnOver = turnOver;
    }

    /**
     * Returns the value of the last roll.
     *
     * @return the last roll value
     */
    public int getLastRoll() {
        return lastRoll;
    }

    /**
     * Sets the value of the last roll.
     *
     * @param lastRoll the new last roll value
     */
    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }

}