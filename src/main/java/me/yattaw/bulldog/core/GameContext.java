package me.yattaw.bulldog.core;

import me.yattaw.bulldog.states.GameState;

public class GameContext {

    private GameState currentState;

    public GameContext(GameState initialState) {
        this.currentState = initialState;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
    }

    public void initializeGame() {
        currentState.initializeGame();
    }

    public void startGame() {
        currentState.startGame();
    }

    public void playTurn() {
        currentState.playTurn();
    }

    public void displayResults() {
        currentState.displayResults();
    }

}