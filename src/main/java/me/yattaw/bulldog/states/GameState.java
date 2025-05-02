package me.yattaw.bulldog.states;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * GameState interface: Defines the contract for game state implementations in the Bulldog game
 * Specifies methods for initializing the game, starting gameplay, playing turns, and displaying results.
 */
public interface GameState {

    void initializeGame();
    void startGame();
    void playTurn();
    void displayResults();

}