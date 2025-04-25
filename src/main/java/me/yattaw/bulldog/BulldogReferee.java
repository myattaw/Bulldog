package me.yattaw.bulldog;

import me.yattaw.bulldog.core.GameEngine;
import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.core.players.Player;
import me.yattaw.bulldog.core.players.types.HumanPlayer;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * BulldogReferee class:
 * Central controller for managing the Bulldog game session.
 * Handles player turns, dice rolls, scoring, and turn transitions.
 * Coordinates interactions between human/AI players and the game state.
 * Notifies listeners of game events such as start, end, dice rolls, and score updates.
 */
public class BulldogReferee {

    // Singleton instance
    private static final BulldogReferee instance = new BulldogReferee();

    private PlayerModel playerModel;
    private int currentPlayerIndex;
    private boolean gameInProgress;
    private int currentRoundScore;

    // Game event listeners
    private GameEventListener eventListener;

    // Private constructor for singleton
    private BulldogReferee() {
        // Initialize with default values
        reset();
    }

    /**
     * Gets the singleton instance of BulldogReferee
     */
    public static BulldogReferee getInstance() {
        return instance;
    }

    /**
     * Initializes or resets the referee for a new game
     */
    public void initialize(PlayerModel playerModel) {
        this.playerModel = playerModel;
        reset();
    }

    /**
     * Resets game state while keeping the same player model
     */
    private void reset() {
        currentPlayerIndex = 0;
        gameInProgress = false;
        currentRoundScore = 0;
    }

    /**
     * Sets the game event listener for callbacks
     */
    public void setEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    /**
     * Starts a new game session
     */
    public void startGame() {
        if (playerModel == null) {
            throw new IllegalStateException("PlayerModel must be initialized before starting game");
        }

        reset();
        gameInProgress = true;
        notifyGameStarted();
        startNextTurn();
    }

    /**
     * Handles dice roll action
     */
    public void rollDice() {
        if (!gameInProgress) return;

        Player currentPlayer = getCurrentPlayer();
        int roll = currentPlayer.rollDie();
        notifyDiceRolled(currentPlayer, roll);

        if (roll == 6) {
            currentRoundScore = 0;
            notifyTurnEnded(currentPlayer, false);
            endTurn();
        } else {
            currentRoundScore += roll;
        }
    }

    /**
     * Handles stop turn action
     */
    public void stopTurn() {
        if (!gameInProgress) return;
        notifyTurnEnded(getCurrentPlayer(), true);
        endTurn();
    }

    /**
     * Gets the current player
     */
    public Player getCurrentPlayer() {
        return playerModel.getAllPlayers().get(currentPlayerIndex);
    }

    /**
     * Checks if game is in progress
     */
    public boolean isGameInProgress() {
        return gameInProgress;
    }

    /**
     * Gets current round score
     */
    public int getCurrentRoundScore() {
        return currentRoundScore;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    private void startNextTurn() {
        Player currentPlayer = getCurrentPlayer();
        notifyTurnStarted(currentPlayer);

        if (!(currentPlayer instanceof HumanPlayer)) {
            handleAITurn(currentPlayer);
        }
    }

    private void handleAITurn(Player aiPlayer) {
        int roundScore = aiPlayer.play();
        currentRoundScore = roundScore;
        notifyAIPlayed(aiPlayer, roundScore);
        endTurn();
    }

    private void endTurn() {
        Player currentPlayer = getCurrentPlayer();
        int newScore = currentPlayer.getScore() + currentRoundScore;
        playerModel.setPlayerScore(currentPlayerIndex, newScore);

        notifyScoresUpdated();

        if (newScore >= GameEngine.WINNING_SCORE) {
            gameInProgress = false;
            notifyGameEnded(currentPlayer);
            return;
        }

        currentRoundScore = 0;
        currentPlayerIndex = (currentPlayerIndex + 1) % playerModel.getPlayerCount();
        startNextTurn();
    }

    // Notification methods to event listener
    private void notifyGameStarted() {
        if (eventListener != null) {
            eventListener.onGameStarted();
        }
    }

    private void notifyTurnStarted(Player player) {
        if (eventListener != null) {
            eventListener.onTurnStarted(player);
        }
    }

    private void notifyDiceRolled(Player player, int value) {
        if (eventListener != null) {
            eventListener.onDiceRolled(player, value);
        }
    }

    private void notifyTurnEnded(Player player, boolean byChoice) {
        if (eventListener != null) {
            eventListener.onTurnEnded(player, byChoice);
        }
    }

    private void notifyAIPlayed(Player player, int score) {
        if (eventListener != null) {
            eventListener.onAIPlayed(player, score);
        }
    }

    private void notifyScoresUpdated() {
        if (eventListener != null) {
            eventListener.onScoresUpdated();
        }
    }

    private void notifyGameEnded(Player winner) {
        if (eventListener != null) {
            eventListener.onGameEnded(winner);
        }
    }

    /**
     * Interface for game event callbacks
     */
    public interface GameEventListener {
        void onGameStarted();
        void onTurnStarted(Player player);
        void onDiceRolled(Player player, int value);
        void onTurnEnded(Player player, boolean byChoice);
        void onAIPlayed(Player player, int score);
        void onScoresUpdated();
        void onGameEnded(Player winner);
    }
}