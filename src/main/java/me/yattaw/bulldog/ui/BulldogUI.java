package me.yattaw.bulldog.ui;

import me.yattaw.bulldog.core.model.PlayerModel;
import me.yattaw.bulldog.ui.panels.GamePlayPanel;
import me.yattaw.bulldog.ui.panels.MainMenuPanel;
import me.yattaw.bulldog.ui.scoreboard.ScoreboardViewer;

import javax.swing.*;
import java.awt.*;


/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * BulldogUI class: Handles the graphical user interface for the Bulldog game.
 * Manages screen transitions between the main menu and gameplay.
 */
public class BulldogUI extends JFrame {

    /** Layout manager for switching between different game panels. */
    private CardLayout cardLayout;

    /** Main container panel holding different game screens. */
    private JPanel mainPanel;

    /** Panel for the main menu. */
    private MainMenuPanel mainMenuPanel;

    /** Panel for the gameplay screen. */
    private GamePlayPanel gamePlayPanel;

    /**
     * Initializes the Bulldog game UI with a main menu and gameplay panel.
     */
    public BulldogUI() {
        setTitle("Bulldog Game");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        mainMenuPanel = new MainMenuPanel(new PlayerModel());
        gamePlayPanel = new GamePlayPanel();

        mainPanel.add(mainMenuPanel, "MainMenu");
        mainPanel.add(gamePlayPanel, "GamePlay");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");
        setVisible(true);
    }

    /**
     * Starts the game with the provided players and switches to the gameplay screen.
     * @param playerModel      A model that handles players participating in the game.
     * @param scoreboardViewer A JFrame that live updates with players scores.
     */
    public void startGame(PlayerModel playerModel, ScoreboardViewer scoreboardViewer) {
        gamePlayPanel.startGame(playerModel, scoreboardViewer);
        cardLayout.show(mainPanel, "GamePlay");
    }

    /**
     * Returns to the main menu screen.
     */
    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
    }

}