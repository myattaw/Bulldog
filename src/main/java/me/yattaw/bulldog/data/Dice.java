package me.yattaw.bulldog.data;

import java.util.Random;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Dice Class: Represents a standard die that can be rolled.
 */
public class Dice extends RandomDice {

    /** A shared instance of Random used for rolling the die. */
    private static final Random RANDOM = new Random();

    /** The single shared instance of Dice with 6 sides. */
    public static final Dice DEFAULT_DIE = new Dice(6);

    /**
     * Constructs a Dice object with a specified number of sides.
     *
     * @param sides The number of sides on the die.
     */
    public Dice(int sides) {
        super(sides);
    }

    /**
     * Rolls the die and returns a random number between 1 and the number of sides (inclusive).
     *
     * @return A randomly generated number between 1 and the number of sides.
     */
    @Override
    public int roll() {
        return RANDOM.nextInt(getSides()) + 1;
    }

}