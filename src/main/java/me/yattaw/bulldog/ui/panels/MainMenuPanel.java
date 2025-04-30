package me.yattaw.bulldog.ui.panels;

import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.reflection.ReflectionHelper;
import me.yattaw.bulldog.ui.BulldogUI;
import me.yattaw.bulldog.ui.scoreboard.ScoreboardViewer;

import javax.swing.*;
import java.awt.*;
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
    private final JPanel playersPanel;

    /** Layout constraints for dynamically positioning elements. */
    private final GridBagConstraints gbc;

    /** List of players added in the menu. */
    private final PlayerModel playerModel;
    private ScoreboardViewer scoreboardViewer;

    /**
     * Constructs the main menu panel with UI components for adding players and starting a game.
     */
    public MainMenuPanel(PlayerModel playerModel) {
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

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JCheckBox toggleScoreboard = new JCheckBox("Live Scoreboard");
        toggleScoreboard.setPreferredSize(new Dimension(150, 30)); // Smaller size

        JButton startGameButton = new JButton("Start Match");
        startGameButton.addActionListener(e -> {
            startGame(toggleScoreboard.isSelected());
        });
        startGameButton.setPreferredSize(new Dimension(200, 40)); // Larger size

        // Add both to the southPanel
        southPanel.add(toggleScoreboard);
        southPanel.add(startGameButton);

        // Add the southPanel to SOUTH
        add(southPanel, BorderLayout.SOUTH);

        this.playerModel = playerModel;
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

        List<String> availableTypes = ReflectionHelper.getAvailablePlayerTypes();
        JComboBox<String> playerTypeCombo = new JComboBox<>(availableTypes.toArray(new String[0]));

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
     *
     * @param playerContainer The container panel that holds the player's components.
     * @param index           The index of the player, used to determine their position in the display.
     */
    private void updatePlayerLabel(JPanel playerContainer, int index) {
        Arrays.stream(playerContainer.getComponents())
                .filter(c -> c instanceof JPanel)
                .map(c -> (JPanel) c)
                .flatMap(playerPanel -> Arrays.stream(playerPanel.getComponents()))
                .filter(playerComponent -> playerComponent instanceof JLabel)
                .map(playerComponent -> (JLabel) playerComponent)
                .findFirst()
                .ifPresent(playerLabel -> playerLabel.setText(String.format(PLAYER_INFO_LABEL, index + 1)));
    }

    /**
     * Initializes the game by collecting player data from the user interface,
     * adding the players to the model, and starting the match. The method also
     * handles enabling or disabling the scoreboard based on the provided flag.
     *
     * @param scoreboardEnabled A boolean flag indicating whether the scoreboard
     *                          should be enabled for the match. If true, the
     *                          scoreboard will be created and displayed.
     */
    private void startGame(boolean scoreboardEnabled) {
        playerModel.removeAll();
        for (Component comp : playersPanel.getComponents()) {
            if (comp instanceof JPanel playerContainer) {
                JPanel inputPanel = (JPanel) playerContainer.getComponent(0);
                Component[] inputComponents = inputPanel.getComponents();

                String name = ((JTextField) inputComponents[3]).getText();
                String type = (String) ((JComboBox<?>) inputComponents[5]).getSelectedItem();

                if (!name.isEmpty()) {
                    playerModel.addPlayer(ReflectionHelper.createPlayerInstance(type));
                }
            }
        }

        if (playerModel.getAllPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one player.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (scoreboardEnabled) {
            this.scoreboardViewer = new ScoreboardViewer();
        }

        ((BulldogUI) SwingUtilities.getWindowAncestor(this)).startGame(playerModel, scoreboardViewer);
    }

}