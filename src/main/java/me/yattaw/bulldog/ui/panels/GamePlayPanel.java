package me.yattaw.bulldog.ui.panels;

import me.yattaw.bulldog.BulldogReferee;
import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.core.players.Player;
import me.yattaw.bulldog.core.players.types.HumanPlayer;
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
public class GamePlayPanel extends JPanel implements BulldogReferee.GameEventListener {

    private JTextArea transcriptArea;
    private JButton rollDiceButton, stopTurnButton, mainMenuButton;
    private BulldogReferee referee;
    private ScoreboardViewer scoreboardViewer;

    public GamePlayPanel() {
        setLayout(new BorderLayout());

        // Initialize UI components
        transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);
        add(new JScrollPane(transcriptArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());

        rollDiceButton = new JButton("ROLL DICE");
        rollDiceButton.addActionListener(e -> referee.rollDice());
        controlPanel.add(rollDiceButton);

        stopTurnButton = new JButton("STOP TURN");
        stopTurnButton.addActionListener(e -> referee.stopTurn());
        controlPanel.add(stopTurnButton);

        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.addActionListener(e -> ((BulldogUI) SwingUtilities.getWindowAncestor(this)).showMainMenu());
        mainMenuButton.setEnabled(false);
        controlPanel.add(mainMenuButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Get the singleton instance
        referee = BulldogReferee.getInstance();
    }

    public void startGame(PlayerModel playerModel, ScoreboardViewer scoreboardViewer) {
        this.scoreboardViewer = scoreboardViewer;
        referee.initialize(playerModel);
        referee.setEventListener(this);
        referee.startGame();
    }

    @Override
    public void onGameStarted() {
        updateTranscript("Game started!");
        rollDiceButton.setEnabled(true);
        stopTurnButton.setEnabled(true);
        mainMenuButton.setEnabled(false);
    }

    @Override
    public void onTurnStarted(Player player) {
        updateTranscript("It's " + player.getName() + "'s turn!");
        if (player instanceof HumanPlayer) {
            rollDiceButton.setEnabled(true);
            stopTurnButton.setEnabled(true);
        } else {
            rollDiceButton.setEnabled(false);
            stopTurnButton.setEnabled(false);
        }
    }

    @Override
    public void onDiceRolled(Player player, int value) {
        updateTranscript(player.getName() + " rolled a " + value + ".");
    }

    @Override
    public void onTurnEnded(Player player, boolean byChoice) {
        if (!byChoice) {
            updateTranscript("Turn ended automatically.");
        }
    }

    @Override
    public void onAIPlayed(Player player, int score) {
        updateTranscript(player.getName() + " scored " + score + " points.");
    }

    @Override
    public void onScoresUpdated() {
        updateTranscript("Current scores:");
        List<Player> players = referee.getPlayerModel().getAllPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            updateTranscript(" - Player " + (i + 1) + " '" + player.getName() + "': " + player.getScore() + " points");
        }
        if (scoreboardViewer != null) {
            scoreboardViewer.update(referee.getPlayerModel());
        }
    }

    @Override
    public void onGameEnded(Player winner) {
        updateTranscript(winner.getName() + " wins the game with a score of " + winner.getScore() + "!");
        rollDiceButton.setEnabled(false);
        stopTurnButton.setEnabled(false);
        mainMenuButton.setEnabled(true);
    }

    private void updateTranscript(String message) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timestamp = currentTime.format(formatter);
        transcriptArea.append("[" + timestamp + "]: " + message + "\n");
    }
}