package me.yattaw.bulldog.ui;

import me.yattaw.bulldog.BulldogApplication;
import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.*;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BulldogUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel; // Panel to hold all cards
    private JPanel mainMenuPanel; // Main menu card
    private JPanel gamePlayPanel; // Game play card
    private JTextArea transcriptArea; // Transcript for game play
    private JButton rollDiceButton, stopTurnButton;
    private final List<Player> players;
    private int currentPlayerIndex;
    private boolean gameInProgress;
    private int currentRoundScore; // Track the current round score for the active player

    public BulldogUI() {
        setTitle("Bulldog Game");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        players = new ArrayList<>();
        currentPlayerIndex = 0;
        gameInProgress = false;
        currentRoundScore = 0;

        // Create and add panels
        createMainMenuPanel();
        createGamePlayPanel();
        mainPanel.add(mainMenuPanel, "MainMenu");
        mainPanel.add(gamePlayPanel, "GamePlay");

        // Set the main panel as the content pane
        setContentPane(mainPanel);

        // Show the main menu initially
        cardLayout.show(mainPanel, "MainMenu");
        setVisible(true);
    }

    private void createMainMenuPanel() {
        mainMenuPanel = new JPanel(new BorderLayout());

        // Add Player Button at the top
        JButton addPlayerButton = new JButton("ADD PLAYER");
        addPlayerButton.addActionListener(e -> addPlayerContainer());
        mainMenuPanel.add(addPlayerButton, BorderLayout.NORTH);

        // Panel to hold player containers (side by side)
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainMenuPanel.add(new JScrollPane(playersPanel), BorderLayout.CENTER);

        // Start Game Button at the bottom
        JButton startGameButton = new JButton("START GAME");
        startGameButton.addActionListener(e -> startGame(playersPanel));
        mainMenuPanel.add(startGameButton, BorderLayout.SOUTH);
    }

    private void addPlayerContainer() {
        JPanel playerContainer = new JPanel(new BorderLayout());

        // Panel for name and combo box
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel playerLabel = new JLabel("Player Information");
        inputPanel.add(playerLabel);
        inputPanel.add(new Label()); // Empty label for alignment

        JTextField nameField = new JTextField(10);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        JComboBox<String> playerTypeCombo = new JComboBox<>(new String[]{"AI", "Fifteen", "Human", "Random", "Unique", "Wimp"});
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(playerTypeCombo);

        playerContainer.add(inputPanel, BorderLayout.CENTER);

        // Remove Player button
        JButton removePlayerButton = new JButton("Remove Player");
        removePlayerButton.addActionListener(e -> {
            // Retrieve the playersPanel from the JScrollPane
            JScrollPane scrollPane = (JScrollPane) mainMenuPanel.getComponent(1);
            JPanel playersPanel = (JPanel) scrollPane.getViewport().getView();
            playersPanel.remove(playerContainer);
            playersPanel.revalidate();
            playersPanel.repaint();
        });
        playerContainer.add(removePlayerButton, BorderLayout.SOUTH);

        // Retrieve the playersPanel from the JScrollPane
        JScrollPane scrollPane = (JScrollPane) mainMenuPanel.getComponent(1);
        JPanel playersPanel = (JPanel) scrollPane.getViewport().getView();
        playersPanel.add(playerContainer);
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private void startGame(JPanel playersPanel) {
        players.clear();

        // Iterate through player containers to add players
        for (Component comp : playersPanel.getComponents()) {
            if (comp instanceof JPanel playerContainer) {
                JPanel inputPanel = (JPanel) playerContainer.getComponent(0);
                Component[] inputComponents = inputPanel.getComponents();

                String name = ((JTextField) inputComponents[3]).getText();
                String type = (String) ((JComboBox<?>) inputComponents[5]).getSelectedItem();

                if (!name.isEmpty()) {
                    switch (type) {
                        case "AI" -> players.add(new AIUniquePlayer(name));
                        case "Fifteen" -> players.add(new FifteenPlayer(name));
                        case "Human" -> players.add(new HumanPlayer(name));
                        case "Random" -> players.add(new RandomPlayer(name));
                        case "Unique" -> players.add(new UniquePlayer(name));
                        case "Wimp" -> players.add(new WimpPlayer(name));
                    }
                }
            }
        }

        if (players.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one player.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Switch to the game play panel
        cardLayout.show(mainPanel, "GamePlay");
        gameInProgress = true;
        currentPlayerIndex = 0;
        currentRoundScore = 0; // Reset round score
        startNextTurn();
    }

    private void createGamePlayPanel() {
        gamePlayPanel = new JPanel(new BorderLayout());

        // Transcript area
        transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);

        // Redirect System.out to the transcript area
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(transcriptArea));
        System.setOut(printStream);

        gamePlayPanel.add(new JScrollPane(transcriptArea), BorderLayout.CENTER);

        // Control panel for buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        rollDiceButton = new JButton("ROLL DICE");
        rollDiceButton.addActionListener(e -> rollDice());
        controlPanel.add(rollDiceButton);

        stopTurnButton = new JButton("STOP TURN");
        stopTurnButton.addActionListener(e -> stopTurn());
        controlPanel.add(stopTurnButton);

        gamePlayPanel.add(controlPanel, BorderLayout.SOUTH);
    }

    private void startNextTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        updateTranscript("It's " + currentPlayer.getName() + "'s turn!");

        if (currentPlayer instanceof HumanPlayer) {
            rollDiceButton.setEnabled(true);
            stopTurnButton.setEnabled(true);
        } else {
            rollDiceButton.setEnabled(false);
            stopTurnButton.setEnabled(false);
            // AI or other players take their turn automatically
            int roundScore = currentPlayer.play();
            updateTranscript(currentPlayer.getName() + " scored " + roundScore + " points.");
            currentRoundScore = roundScore; // Track the round score
            endTurn();
        }
    }

    private void rollDice() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer instanceof HumanPlayer) {
            int roll = currentPlayer.rollDie();
            updateTranscript(currentPlayer.getName() + " rolled a " + roll + ".");

            if (roll == 6) {
                updateTranscript("Rolled a 6. Turn ends with 0 points.");
                currentRoundScore = 0; // Set round score to 0
                endTurn();
            } else {
                currentRoundScore += roll; // Add to the round score
            }
        }
    }

    private void stopTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer instanceof HumanPlayer) {
            updateTranscript(currentPlayer.getName() + " chose to stop.");
            endTurn();
        }
    }

    private void endTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.setScore(currentPlayer.getScore() + currentRoundScore); // Update total score
        updateScores(); // Display updated scores

        if (currentPlayer.getScore() >= BulldogApplication.WINNING_SCORE) {
            updateTranscript(currentPlayer.getName() + " wins the game with a score of " + currentPlayer.getScore() + "!");
            gameInProgress = false;
            rollDiceButton.setEnabled(false);
            stopTurnButton.setEnabled(false);
            return;
        }

        currentRoundScore = 0; // Reset round score for the next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        startNextTurn();
    }

    private void updateScores() {
        updateTranscript("Current scores:");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            updateTranscript(" - Player " + (i + 1) + " '" + player.getName() + "': " + player.getScore() + " points");
        }
        transcriptArea.append("\n");
    }

    private void updateTranscript(String message) {
        // Get the current time and format it as HH:MM:ss
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timestamp = currentTime.format(formatter);

        // Append the timestamp and message to the transcript area with the specified format
        transcriptArea.append("[" + timestamp + "]: " + message + "\n");
    }

    public static void main(String[] args) {
        new BulldogUI();
    }
}