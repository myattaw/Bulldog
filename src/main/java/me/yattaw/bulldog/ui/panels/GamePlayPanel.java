package me.yattaw.bulldog.ui.panels;

import me.yattaw.bulldog.BulldogApplication;
import me.yattaw.bulldog.model.PlayerModel;
import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.HumanPlayer;
import me.yattaw.bulldog.ui.BulldogUI;
import me.yattaw.bulldog.ui.scoreboard.ScoreboardViewer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * GamePlayPanel class: Manages gameplay interactions, including rolling dice and tracking scores.
 */
public class GamePlayPanel extends JPanel {

    /** Area for displaying game events and updates. */
    private JTextArea transcriptArea;

    /** Buttons for rolling dice, stopping a turn, and returning to the main menu. */
    private JButton rollDiceButton, stopTurnButton, mainMenuButton;

    /** Index of the current player's turn. */
    private int currentPlayerIndex;

    /** Flag indicating if a game is in progress. */
    private boolean gameInProgress;

    /** Tracks the current round score. */
    private int currentRoundScore;

    /** List of players added in the menu. */
    private PlayerModel playerModel;

    private ScoreboardViewer scoreboardViewer;

    /**
     * Constructs the game panel and initializes UI components for gameplay.
     */
    public GamePlayPanel() {
        setLayout(new BorderLayout());

        // Create a non-editable text area for displaying the game transcript
        transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);
        add(new JScrollPane(transcriptArea), BorderLayout.CENTER);

        // Create a panel to hold control buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Initialize the Roll Dice button with an action listener
        rollDiceButton = new JButton("ROLL DICE");
        rollDiceButton.addActionListener(e -> rollDice());
        controlPanel.add(rollDiceButton);

        // Initialize the Stop Turn button with an action listener
        stopTurnButton = new JButton("STOP TURN");
        stopTurnButton.addActionListener(e -> stopTurn());
        controlPanel.add(stopTurnButton);

        // Initialize the Main Menu button and disable it initially
        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.addActionListener(e -> ((BulldogUI) SwingUtilities.getWindowAncestor(this)).showMainMenu());
        mainMenuButton.setEnabled(false);
        controlPanel.add(mainMenuButton);

        // Add control panel to the bottom of the layout
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Starts a new game session with the given list of players.
     * Resets the game state and begins the first player's turn.
     *
     * @param playerModel      A model that handles players participating in the game.
     * @param scoreboardViewer A JFrame that live updates with players scores.
     */
    public void startGame(PlayerModel playerModel, ScoreboardViewer scoreboardViewer) {
        this.playerModel = playerModel;
        this.scoreboardViewer = scoreboardViewer;
        currentPlayerIndex = 0;
        gameInProgress = true;
        currentRoundScore = 0;
        startNextTurn();
    }

    /**
     * Initiates the next player's turn in the game.
     * Displays whose turn it is and enables or disables controls based on player type.
     */
    private void startNextTurn() {
        Player currentPlayer = playerModel.getAllPlayers().get(currentPlayerIndex);
        updateTranscript("It's " + currentPlayer.getName() + "'s turn!");

        // If the player is human, enable roll and stop buttons
        if (currentPlayer instanceof HumanPlayer) {
            rollDiceButton.setEnabled(true);
            stopTurnButton.setEnabled(true);
        }
        // If the player is AI, disable buttons and let the AI play automatically
        else {
            rollDiceButton.setEnabled(false);
            stopTurnButton.setEnabled(false);
            int roundScore = currentPlayer.play();
            updateTranscript(currentPlayer.getName() + " scored " + roundScore + " points.");
            currentRoundScore = roundScore;
            endTurn();
        }
    }

    /**
     * Handles the logic for rolling the dice during a turn.
     * If a 6 is rolled, the turn ends with zero points.
     * Otherwise, the rolled value is added to the round score.
     */
    private void rollDice() {
        Player currentPlayer = playerModel.getAllPlayers().get(currentPlayerIndex);
        if (currentPlayer instanceof HumanPlayer) {
            int roll = currentPlayer.rollDie();
            updateTranscript(currentPlayer.getName() + " rolled a " + roll + ".");

            // If the player rolls a 6, they lose all points for the round and the turn ends
            if (roll == 6) {
                updateTranscript("Rolled a 6. Turn ends with 0 points.");
                currentRoundScore = 0;
                endTurn();
            } else {
                // Add the rolled value to the round score
                currentRoundScore += roll;
            }
        }
    }

    /**
     * Handles the player's decision to stop their turn.
     * Adds the current round score to their total score and proceeds to the next player.
     */
    private void stopTurn() {
        Player currentPlayer = playerModel.getAllPlayers().get(currentPlayerIndex);
        if (currentPlayer instanceof HumanPlayer) {
            updateTranscript(currentPlayer.getName() + " chose to stop.");
            endTurn();
        }
    }

    /**
     * Ends the current player's turn.
     * Updates the total score, checks for a winner, and moves to the next player.
     */
    private void endTurn() {
        Player currentPlayer = playerModel.getAllPlayers().get(currentPlayerIndex);

        // Add the round score to the player's total score
        playerModel.setPlayerScore(currentPlayerIndex, currentPlayer.getScore() + currentRoundScore);

        updateScores();

        // Check if the player has reached the winning score
        if (currentPlayer.getScore() >= BulldogApplication.WINNING_SCORE) {
            updateTranscript(currentPlayer.getName() + " wins the game with a score of " + currentPlayer.getScore() + "!");
            gameInProgress = false;
            rollDiceButton.setEnabled(false);
            stopTurnButton.setEnabled(false);
            mainMenuButton.setEnabled(true);
            return;
        }

        // Reset round score and switch to the next player
        currentRoundScore = 0;
        currentPlayerIndex = (currentPlayerIndex + 1) % playerModel.getPlayerCount();
        startNextTurn();
    }

    /**
     * Updates the transcript with the current scores of all players.
     */
    private void updateScores() {
        updateTranscript("Current scores:");
        List<Player> players = playerModel.getAllPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            updateTranscript(" - Player " + (i + 1) + " '" + player.getName() + "': " + player.getScore() + " points");
        }
        transcriptArea.append("\n");
        if (scoreboardViewer != null) {
            scoreboardViewer.update(playerModel);
        }
    }

    /**
     * Adds a timestamped message to the transcript area.
     *
     * @param message The message to be displayed.
     */
    private void updateTranscript(String message) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timestamp = currentTime.format(formatter);
        transcriptArea.append("[" + timestamp + "]: " + message + "\n");
    }
}