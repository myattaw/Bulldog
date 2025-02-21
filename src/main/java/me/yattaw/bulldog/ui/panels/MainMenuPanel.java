package me.yattaw.bulldog.ui.panels;

import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.*;
import me.yattaw.bulldog.ui.BulldogUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * MainMenuPanel class: Provides the UI for adding players and starting a new match.
 */
public class MainMenuPanel extends JPanel {

    /** Label format for displaying player information. */
    public static final String PLAYER_INFO_LABEL = "Player %d Details:";

    /** Panel to hold player entry fields. */
    private JPanel playersPanel;

    /** Layout constraints for dynamically positioning elements. */
    private GridBagConstraints gbc;

    /** List of players added in the menu. */
    private List<Player> players;

    /**
     * Constructs the main menu panel with UI components for adding players and starting a game.
     */
    public MainMenuPanel() {
        setLayout(new BorderLayout());

        JButton addPlayerButton = new JButton("Add New Player");
        addPlayerButton.addActionListener(e -> addPlayerContainer());
        add(addPlayerButton, BorderLayout.NORTH);

        playersPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JScrollPane scrollPane = new JScrollPane(playersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        JButton startGameButton = new JButton("Start Match");
        startGameButton.addActionListener(e -> startGame());
        add(startGameButton, BorderLayout.SOUTH);

        players = new ArrayList<>();
    }

    /**
     * Adds a new player entry panel to the UI.
     */
    private void addPlayerContainer() {
        JPanel playerContainer = new JPanel(new BorderLayout());
        playerContainer.setPreferredSize(new Dimension(230, 120));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel playerLabel = new JLabel(String.format(PLAYER_INFO_LABEL, playersPanel.getComponentCount() + 1));
        inputPanel.add(playerLabel);
        inputPanel.add(new JLabel());

        JTextField nameField = new JTextField(10);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        JComboBox<String> playerTypeCombo = new JComboBox<>(new String[]{"AI", "Fifteen", "Human", "Random", "Unique", "Wimp"});
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(playerTypeCombo);

        playerContainer.add(inputPanel, BorderLayout.CENTER);

        JButton removePlayerButton = new JButton("Remove Player");
        removePlayerButton.addActionListener(e -> {
            playersPanel.remove(playerContainer);
            rearrangePlayers();
            playersPanel.revalidate();
            playersPanel.repaint();
        });

        playerContainer.add(removePlayerButton, BorderLayout.SOUTH);

        gbc.gridx = playersPanel.getComponentCount() % 3;
        gbc.gridy = playersPanel.getComponentCount() / 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        playersPanel.add(playerContainer, gbc);
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    /**
     * Rearranges player entries after a player is removed.
     */
    private void rearrangePlayers() {
        Component[] components = playersPanel.getComponents();
        int index = 0;
        for (Component comp : components) {
            if (comp instanceof JPanel playerContainer) {

                updatePlayerLabel(playerContainer, index);

                gbc.gridx = index % 3;
                gbc.gridy = index / 3;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.CENTER;
                playersPanel.add(playerContainer, gbc);

                index++;
            }
        }

        playersPanel.revalidate();
        playersPanel.repaint();
    }

    /**
     * Updates the label of a player within the specified player container.
     * The label is updated to display the player's position (index + 1) using the predefined format {@link #PLAYER_INFO_LABEL}.
     *
     * @param playerContainer The container panel that holds the player's components.
     * @param index           The index of the player, used to determine their position in the display.
     */
    private void updatePlayerLabel(JPanel playerContainer, int index) {
        Arrays.stream(playerContainer.getComponents()).filter(c -> c instanceof JPanel)
                .map(c -> (JPanel) c).flatMap(playerPanel -> Arrays.stream(playerPanel.getComponents()))
                .filter(playerComponent -> playerComponent instanceof JLabel)
                .map(playerComponent -> (JLabel) playerComponent).findFirst()
                .ifPresent(playerLabel -> playerLabel.setText(String.format(PLAYER_INFO_LABEL, index + 1)));
    }

    /**
     * Starts the game by collecting player data and initializing the match.
     */
    private void startGame() {
        players.clear();

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

        // Notify the main UI to switch to the game play panel
        ((BulldogUI) SwingUtilities.getWindowAncestor(this)).startGame(players);
    }

    public List<Player> getPlayers() {
        return players;
    }
}