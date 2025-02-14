package  me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

/********************************************************/
/* Michael Yattaw                                       */
/* Login ID: michael.yattaw@maine.edu                   */
/* COS 420, Spring 2025                                 */
/* Bulldog Project                                      */
/* RandomPlayer class: extends Player class             */
/*           A RandomPlayer stops rolling based on a    */
/*           random chance (50% each turn)              */
/********************************************************/
public class RandomPlayer extends Player {

    private static final double CONTINUE_CHANCE = 0.5;

    /********************************************************/
    /* Constructor: RandomPlayer                            */
    /* Purpose: Create a default RandomPlayer               */
    /* Parameters:                                          */
    /*   none                                               */
    /********************************************************/
    public RandomPlayer() {
        super("Random");
    }

    /********************************************************/
    /* Constructor: RandomPlayer                            */
    /* Purpose: Create a new RandomPlayer object            */
    /* Parameters:                                          */
    /*   String name:  the name of the Player being created */
    /********************************************************/
    public RandomPlayer(String name) {
        super(name);
    }

    /********************************************************/
    /* Method:  play                                        */
    /* Purpose: Take turns until the randomly generated     */
    /*          value is greater than 0.5 (50% chance)      */
    /* Parameters:                                          */
    /*   none                                               */
    /* Returns:                                             */
    /*   the score earned by the player on this turn,       */
    /*       which will be zero if a six was rolled         */
    /********************************************************/
    @Override
    public int play() {
        int roundScore = 0;

        do {
            int roll = rollDie();
            if (roll == 6) {
                System.out.printf("   Player %s rolled %d, so player scored 0 for the turn.%n", getName(), roll);
                return 0;
            } else {
                roundScore += roll;
                System.out.printf("   Player %s rolled %d, cumulative score is %d%n", getName(), roll, roundScore);
            }
        } while (Math.random() < CONTINUE_CHANCE);

        System.out.printf("   Player %s chose not to continue, scoring %d for the turn%n", getName(), roundScore);
        return roundScore;
    }


}
