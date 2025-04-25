package me.yattaw.bulldog;

import me.yattaw.bulldog.core.GameContext;
import me.yattaw.bulldog.core.GameEngine;
import me.yattaw.bulldog.core.players.Player;
import me.yattaw.bulldog.core.players.types.*;
import me.yattaw.bulldog.states.ConsoleState;
import me.yattaw.bulldog.states.SwingState;

import javax.swing.*;
import java.util.*;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * BulldogApplication class: Main class for the Bulldog game
 * Manages matches, players, and gameplay until a player reaches the winning score.
 */
public class BulldogApplication {

    /**
     * Entry point for console version of Bulldog game.
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
            GameContext context = new GameContext(new ConsoleState());
            context.initializeGame();
            context.startGame();
        } else {
            SwingUtilities.invokeLater(() -> {
                GameContext context = new GameContext(new SwingState());
                context.initializeGame();
            });
        }
    }

}