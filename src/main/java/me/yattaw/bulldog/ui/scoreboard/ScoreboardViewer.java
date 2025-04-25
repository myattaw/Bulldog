package me.yattaw.bulldog.ui.scoreboard;

import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.core.players.Player;
import me.yattaw.bulldog.core.players.types.RandomPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * ScoreboardViewer class: Displays player information from the PlayerModel
 * and updates the display when the model's data changes.
 */
public class ScoreboardViewer extends JFrame {

    /** Panel for displaying player scores */
    private JPanel scorePanel;

    /**
     * Constructs the scoreboard viewer frame.
     */
    public ScoreboardViewer() {
        // Set the title of the frame
        setTitle("Scoreboard");

        // Set the default close operation and size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize the score panel and set the layout
        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        // Add the score panel inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(scorePanel);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Updates the scoreboard display based on the current state of the PlayerModel.
     *
     * @param model The PlayerModel containing the player data to be displayed.
     */
    public void update(PlayerModel model) {
        // Clear the existing content in the panel
        scorePanel.removeAll();

        // Get the list of players from the model
        List<Player> players = model.getAllPlayers();

        // Add a label for each player showing their name and score
        for (Player player : players) {
            JLabel playerLabel = new JLabel(player.getName() + ": " + player.getScore() + " points");
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the label
            scorePanel.add(playerLabel);
        }

        // Refresh the panel to display the updated player information
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    /**
     * Main method to demonstrate the functionality of the ScoreboardViewer.
     */
    public static void main(String[] args) {
        // Create a PlayerModel and populate it with some players
        PlayerModel model = new PlayerModel();
        model.addPlayer(new RandomPlayer("Alice"));
        model.addPlayer(new RandomPlayer("Bob"));
        model.addPlayer(new RandomPlayer("Charlie"));

        // Create the ScoreboardViewer and display the initial scores
        ScoreboardViewer viewer = new ScoreboardViewer();
        viewer.update(model);

        // Show an OK-only dialog to pause the program
        JOptionPane.showMessageDialog(viewer, "Click OK to update scores.", "Pause", JOptionPane.INFORMATION_MESSAGE);

        // Update the scores in the model
        model.getPlayerByName("Alice").ifPresent(player -> player.setScore(150));
        model.getPlayerByName("Bob").ifPresent(player -> player.setScore(250));

        // Update the viewer to reflect the changes
        viewer.update(model);
    }

}
