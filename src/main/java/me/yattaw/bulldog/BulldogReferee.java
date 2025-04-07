package me.yattaw.bulldog;

import me.yattaw.bulldog.model.PlayerModel;
import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.HumanPlayer;

/**
 * Referee class: Handles all game logic independently of UI components.
 */
public class BulldogReferee {

    private final PlayerModel playerModel;
    private int currentPlayerIndex;
    private boolean gameInProgress;
    private int currentRoundScore;

    // Game event listeners
    private GameEventListener eventListener;

    public BulldogReferee(PlayerModel playerModel) {
        this.playerModel = playerModel;
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
        currentPlayerIndex = 0;
        gameInProgress = true;
        currentRoundScore = 0;
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

        if (newScore >= BulldogApplication.WINNING_SCORE) {
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