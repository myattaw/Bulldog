package me.yattaw.bulldog.core.players.types;

import me.yattaw.bulldog.core.model.GameStatus;
import me.yattaw.bulldog.core.players.Player;

/**
 * David Levine
 * Login ID: david.b.levine@maine.edu
 * COS 497, Summer 2024
 * Programming Assignment 6
 * WimpPlayer class: extends Player class.
 * A WimpPlayer always rolls the die once.
 * See Kettering University, CS-101, Prog 6.
 */
public class WimpPlayer extends Player {

    /**
     * Constructor for creating a default WimpPlayer with the name "Wimp".
     */
    public WimpPlayer() {
        this("Wimp");
    }

    /**
     * Constructor for creating a new WimpPlayer object with a specified name.
     *
     * @param name The name of the Player being created
     */
    public WimpPlayer(String name) {
        super(name);
    }

    /**
     * Determines whether the WimpPlayer wants to continue rolling.
     * A WimpPlayer never continues after the first roll (unless forced by game rules).
     *
     * @param gameStatus The current game status information
     * @return false always (WimpPlayer never continues rolling)
     */
    @Override
    public boolean continueRolling(GameStatus gameStatus) {
        System.out.print("   Player " + getName() + " rolled " + gameStatus.getLastRoll());

        if (gameStatus.getLastRoll() == 6) {
            System.out.println(" and scored 0 for the turn.");
        } else {
            System.out.println(" and chose not to continue, scoring " +
                    gameStatus.getRoundScore() + " for the turn.");
        }

        return false;
    }

}