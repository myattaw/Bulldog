package  me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

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
     * Takes one turn for this Player. A WimpPlayer always rolls the die once.
     * If the roll is a 6, the player scores 0 for the turn. Otherwise, the player
     * scores the value of the roll and chooses not to continue.
     *
     * @return The score earned by the player on this turn, which will be zero if a six was rolled
     */
    @Override
    public int play() {
        int roll = rollDie();
        System.out.print("   Player " + getName() + " rolled " + roll);

        if (roll == 6) {
            System.out.println(" and scored 0 for the turn.");
            return 0;
        } else {
            System.out.println(" and chose not to continue, scoring " + roll + " for the turn.");
            return roll;
        }
    }

}