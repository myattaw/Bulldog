package me.yattaw.bulldog.data;

import java.util.Random;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Dice Class: Represents a standard die that can be rolled.
 */
public class Dice {

    /** A shared instance of Random used for rolling the die. */
    private static final Random RANDOM = new Random();

    /** The single shared instance of Dice with 6 sides. */
    public static final Dice DEFAULT_DIE = new Dice(6);

    /** The number of sides on the die. */
    private final int sides;

    /**
     * Constructs a Dice object with a specified number of sides.
     *
     * @param sides The number of sides on the die. Must be greater than 0.
     * @throws IllegalArgumentException if sides is less than 1.
     */
    public Dice(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("Number of sides must be at least 1");
        }
        this.sides = sides;
    }

    /**
     * Rolls the die and returns a random number between 1 and the number of sides (inclusive).
     *
     * @return A randomly generated number between 1 and {@code sides}.
     */
    public int roll() {
        return RANDOM.nextInt(this.sides) + 1;
    }

    /**
     * Gets the number of sides on the die.
     *
     * @return The number of sides of this die.
     */
    public int getSides() {
        return sides;
    }

}
