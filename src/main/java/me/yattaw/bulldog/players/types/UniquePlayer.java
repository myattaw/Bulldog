package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

/********************************************************/
/* Michael Yattaw                                       */
/* Login ID: michael.yattaw@maine.edu                   */
/* COS 420, Spring 2025                                 */
/* Bulldog Project                                      */
/* UniquePlayer class: extends Player class             */
/*           A UniquePlayer takes turns exponentially   */
/*           each round, doubling the number of rolls   */
/*           until a six is rolled, which resets the    */
/*           exponential counter.                       */
/********************************************************/
public class UniquePlayer extends Player {

    private int exponential;

    /********************************************************/
    /* Constructor: UniquePlayer                            */
    /* Purpose: Create a default UniquePlayer               */
    /* Parameters:                                          */
    /*   none                                               */
    /********************************************************/
    public UniquePlayer() {
        super("Exponential");
    }

    /********************************************************/
    /* Constructor: UniquePlayer                            */
    /* Purpose: Create a new UniquePlayer object            */
    /* Parameters:                                          */
    /*   String name:  the name of the Player being created */
    /********************************************************/
    public UniquePlayer(String name) {
        super(name);
    }

    /********************************************************/
    /* Method:  play                                        */
    /* Purpose: Takes turns exponentially each round until  */
    /*          six is reach which resets exponential value */
    /* Parameters:                                          */
    /*   none                                               */
    /* Returns:                                             */
    /*   the score earned by the player on this turn,       */
    /*       which will be zero if a six was rolled         */
    /********************************************************/
    @Override
    public int play() {
        int roundScore = 0;
        for (int i = 0; i < Math.pow(2, exponential); i++) {
            int roll = rollDie();
            if (roll == 6) {
                exponential = 0;
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        }
        exponential++;
        return roundScore;
    }

}
