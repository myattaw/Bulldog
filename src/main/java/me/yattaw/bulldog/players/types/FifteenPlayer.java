package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

/********************************************************/
/* Michael Yattaw                                       */
/* Login ID: michael.yattaw@maine.edu                   */
/* COS 420, Spring 2025                                 */
/* Bulldog Project                                      */
/* FifteenPlayer class: extends Player class            */
/*           A FifteenPlayer rolls until their score    */
/*           reaches fifteen or greater                 */
/********************************************************/
public class FifteenPlayer extends Player {

    private static final int TARGET_SCORE = 15;

    /********************************************************/
    /* Constructor: FifteenPlayer                           */
    /* Purpose: Create a default FifteenPlayer              */
    /* Parameters:                                          */
    /*   none                                               */
    /********************************************************/
    public FifteenPlayer() {
        super("Fifteen");
    }

    /********************************************************/
    /* Constructor: FifteenPlayer                           */
    /* Purpose: Create a new FifteenPlayer object           */
    /* Parameters:                                          */
    /*   String name:  the name of the Player being created */
    /********************************************************/
    public FifteenPlayer(String name) {
        super(name);
    }

    /********************************************************/
    /* Method:  play                                        */
    /* Purpose: Takes turns until the player rolls          */
    /*          fifteen or greater                          */
    /* Parameters:                                          */
    /*   none                                               */
    /* Returns:                                             */
    /*   the score earned by the player on this turn,       */
    /*       which will be zero if a six was rolled         */
    /********************************************************/
    @Override
    public int play() {
        int roundScore = 0;

        while (roundScore < TARGET_SCORE) {
            int roll = rollDie();
            if (roll == 6) {
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        }

        System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n", getName(), roundScore);
        return roundScore;
    }

}