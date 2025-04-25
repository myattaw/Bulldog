package me.yattaw.bulldog.states;

public interface GameState {

    void initializeGame();
    void startGame();
    void playTurn();
    void displayResults();

}