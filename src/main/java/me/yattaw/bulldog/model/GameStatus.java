package me.yattaw.bulldog.model;

public class GameStatus {

    private int roundScore;
    private boolean turnOver;
    private int lastRoll;

    public GameStatus() {
        this.roundScore = 0;
        this.turnOver = false;
        this.lastRoll = 0;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public boolean isTurnOver() {
        return turnOver;
    }

    public void setTurnOver(boolean turnOver) {
        this.turnOver = turnOver;
    }

    public int getLastRoll() {
        return lastRoll;
    }

    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }

}