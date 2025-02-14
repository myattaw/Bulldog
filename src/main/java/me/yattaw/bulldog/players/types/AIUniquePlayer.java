package me.yattaw.bulldog.players.types;

import me.yattaw.bulldog.players.Player;

/********************************************************/
/* Michael Yattaw                                       */
/* Login ID: michael.yattaw@maine.edu                   */
/* COS 420, Spring 2025                                 */
/* Bulldog Project                                      */
/* UniquePlayer class: extends Player class             */
/*           A UniquePlayer uses a dynamic threshold    */
/*           to decide when to stop rolling             */
/********************************************************/
public class AIUniquePlayer extends Player {

    private static final int BASE_THRESHOLD = 20;
    private static final int MIN_THRESHOLD = 10;
    private static final int SCORE_DIVISOR = 10;

    /********************************************************/
    /* Constructor: UniquePlayer                            */
    /* Purpose: Create a default UniquePlayer               */
    /* Parameters:                                          */
    /*   none                                               */
    /********************************************************/
    public AIUniquePlayer() {
        super("Unique");
    }

    /********************************************************/
    /* Constructor: UniquePlayer                            */
    /* Purpose: Create a new UniquePlayer object            */
    /* Parameters:                                          */
    /*   String name:  the name of the Player being created */
    /********************************************************/
    public AIUniquePlayer(String name) {
        super(name);
    }

    /********************************************************/
    /* Method:  play                                        */
    /* Purpose: Takes turns for this Player, rolling the die*/
    /*          until a 6 is rolled or the dynamic threshold*/
    /*          is reached. The threshold decreases as the  */
    /*          player's overall score increases.           */
    /* Parameters:                                          */
    /*   none                                               */
    /* Returns:                                             */
    /*   the score earned by the player on this turn,       */
    /*       which will be zero if a six was rolled         */
    /********************************************************/
    @Override
    public int play() {
        int turnScore = 0;
        boolean continueRolling = true;

        while (continueRolling) {
            int roll = rollDie();
            System.out.print("   Player " + getName() + " rolled " + roll);

            if (roll == 6) {
                System.out.println(" and scored 0 for the turn.");
                return 0;
            } else {
                turnScore += roll;
                System.out.println(" and has a cumulative score of " + turnScore + " for the turn.");

                int threshold = calculateThreshold();
                if (turnScore >= threshold) {
                    System.out.println("   Player " + getName() + " chose to end the turn with a score of " + turnScore + ".");
                    continueRolling = false;
                } else {
                    System.out.println("   Player " + getName() + " chose to continue rolling.");
                }
            }
        }

        return turnScore;
    }

    /********************************************************/
    /* Method:  calculateThreshold                          */
    /* Purpose: Calculates the dynamic threshold for        */
    /*          deciding when to stop rolling. The threshold*/
    /*          decreases as the player's overall score     */
    /*          increases, but never goes below the minimum */
    /*          threshold (MIN_THRESHOLD).                  */
    /* Parameters:                                          */
    /*   none                                               */
    /* Returns:                                             */
    /*   the calculated threshold value                     */
    /********************************************************/
    private int calculateThreshold() {
        return Math.max(MIN_THRESHOLD, BASE_THRESHOLD - (getScore() / SCORE_DIVISOR));
    }

}
