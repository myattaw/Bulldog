package me.yattaw.bulldog.core;

import me.yattaw.bulldog.states.GameState;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * GameContext class: Manages the state design pattern for the Bulldog game
 * Maintains the current GameState and delegates game operations to it.
 */
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