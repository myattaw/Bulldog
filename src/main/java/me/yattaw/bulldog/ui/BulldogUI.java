package me.yattaw.bulldog.ui;

import me.yattaw.bulldog.BulldogApplication;
import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BulldogUI extends JFrame {
    private JButton addPlayerButton, startGameButton;
    private final JPanel playersPanel;
    private final List<Player> players;

    public BulldogUI() {
        setTitle("Bulldog Game");
        setSize(800, 400); // Increased width to accommodate side-by-side player containers
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        players = new ArrayList<>();

        // Add Player Button at the top
        addPlayerButton = new JButton("ADD PLAYER");
        addPlayerButton.addActionListener(e -> addPlayerContainer());
        add(addPlayerButton, BorderLayout.NORTH);

        // Panel to hold player containers (side by side)
        playersPanel = new JPanel();
        playersPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for side-by-side stacking
        add(new JScrollPane(playersPanel), BorderLayout.CENTER);

        // Start Game Button at the bottom
        startGameButton = new JButton("START GAME");
        startGameButton.addActionListener(e -> startGame());
        add(startGameButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addPlayerContainer() {
        JPanel playerContainer = new JPanel();
        playerContainer.setLayout(new BorderLayout()); // Use BorderLayout for the player container

        // Panel for name and combo box
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5)); // 2 rows, 2 columns for name and combo box

        JLabel playerLabel = new JLabel("Player Information");
        inputPanel.add(playerLabel);
        inputPanel.add(new Label()); // Add empty label for easy alignment.

        JTextField nameField = new JTextField(10);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);

        // Combo box for player type
        JComboBox<String> playerTypeCombo = new JComboBox<>(new String[]{"AI", "Fifteen", "Human", "Random", "Unique", "Wimp"});
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(playerTypeCombo);

        // Add input panel to the center of the player container
        playerContainer.add(inputPanel, BorderLayout.CENTER);

        // Remove Player button at the bottom (stretches across the entire width)
        JButton removePlayerButton = new JButton("Remove Player");
        removePlayerButton.addActionListener(e -> {
            playersPanel.remove(playerContainer);
            playersPanel.revalidate();
            playersPanel.repaint();
        });
        playerContainer.add(removePlayerButton, BorderLayout.SOUTH);

        playersPanel.add(playerContainer);
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private void startGame() {
        players.clear();

        // Iterate through player containers to add players
        for (Component comp : playersPanel.getComponents()) {
            if (comp instanceof JPanel playerContainer) {
                Component[] components = playerContainer.getComponents();
                JPanel inputPanel = (JPanel) ((BorderLayout) playerContainer.getLayout()).getLayoutComponent(BorderLayout.CENTER);
                Component[] inputComponents = inputPanel.getComponents();
                String name = ((JTextField) inputComponents[1]).getText();
                String type = (String) ((JComboBox<?>) inputComponents[3]).getSelectedItem();

                if (type != null && !name.isEmpty()) {
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

        BulldogApplication bulldog = new BulldogApplication();
        bulldog.getMatchPlayers().put("Match", players);
        bulldog.playUntilWinner("Match");
    }

    public static void main(String[] args) {
        new BulldogUI();
    }
}