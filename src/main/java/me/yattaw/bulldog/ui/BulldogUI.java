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
    private JButton rollDiceButton, stopTurnButton, mainMenuButton; // Added mainMenuButton
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

        JButton addPlayerButton = new JButton("Add New Player");
        addPlayerButton.addActionListener(e -> addPlayerContainer());
        mainMenuPanel.add(addPlayerButton, BorderLayout.NORTH);

        // Panel to hold players (flexible but structured)
        JPanel playersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.NORTHWEST; // Aligns items at the top-left

        JScrollPane scrollPane = new JScrollPane(playersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainMenuPanel.add(scrollPane, BorderLayout.CENTER);

        JButton startGameButton = new JButton("Start Match");
        startGameButton.addActionListener(e -> startGame(playersPanel));
        mainMenuPanel.add(startGameButton, BorderLayout.SOUTH);

        // Store panel reference for adding new players
        mainMenuPanel.putClientProperty("playersPanel", playersPanel);
        mainMenuPanel.putClientProperty("gbc", gbc);
    }

    private void addPlayerContainer() {
        JPanel playerContainer = new JPanel(new BorderLayout());
        playerContainer.setPreferredSize(new Dimension(230, 120)); // Fixed size to prevent stretching

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel playerLabel = new JLabel("Player Information");
        inputPanel.add(playerLabel);
        inputPanel.add(new JLabel()); // Empty label for spacing

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
            JScrollPane scrollPane = (JScrollPane) mainMenuPanel.getComponent(1);
            JPanel playersPanel = (JPanel) scrollPane.getViewport().getView();

            // Remove the selected player container
            playersPanel.remove(playerContainer);

            // Call rearrangePlayers to reorder the remaining players in the panel
            rearrangePlayers(playersPanel);

            // Revalidate and repaint the panel
            playersPanel.revalidate();
            playersPanel.repaint();
        });

        playerContainer.add(removePlayerButton, BorderLayout.SOUTH);

        // Retrieve the players panel from mainMenuPanel
        JPanel playersPanel = (JPanel) mainMenuPanel.getClientProperty("playersPanel");

        // Create a new constraints object for each addition
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = playersPanel.getComponentCount() % 3; // Column index (0-2)
        gbc.gridy = playersPanel.getComponentCount() / 3; // New row every 3 players
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.CENTER; // Align items at the top-left

        // Add player container
        playersPanel.add(playerContainer, gbc);

        playersPanel.revalidate();
        playersPanel.repaint();
    }


    private void rearrangePlayers(JPanel playersPanel) {
        // Get the remaining components
        Component[] components = playersPanel.getComponents();

        // Reposition each component based on index
        int index = 0;
        for (Component comp : components) {
            if (comp instanceof JPanel playerContainer) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = index % 3; // Columns
                gbc.gridy = index / 3; // Rows
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.CENTER; // Center items
                playersPanel.add(playerContainer, gbc);

                index++;
            }
        }

        // Revalidate and repaint the panel after rearranging
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

        // Add Main Menu button
        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        mainMenuButton.setEnabled(false); // Initially disabled
        controlPanel.add(mainMenuButton);

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
            mainMenuButton.setEnabled(true); // Enable the Main Menu button
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