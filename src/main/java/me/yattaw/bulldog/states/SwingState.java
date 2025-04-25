package me.yattaw.bulldog.states;

import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.ui.BulldogUI;

public class SwingState implements GameState {

    private BulldogUI bulldogUI;
    private PlayerModel playerModel;

    public SwingState() {
        this.bulldogUI = new BulldogUI();
        this.playerModel = new PlayerModel();
    }

    @Override
    public void initializeGame() {
        // Initialization is handled by the UI components
    }

    @Override
    public void startGame() {
        bulldogUI.startGame(playerModel, null);
    }

    @Override
    public void playTurn() {
        // Handled by the GamePlayPanel
    }

    @Override
    public void displayResults() {
        // Handled by the UI components
    }

}