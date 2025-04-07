package me.yattaw.bulldog.data;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * RandomDice Class: Abstract superclass for random dice.
 * Keeps track of the number of sides and defines an abstract roll() method.
 */
public abstract class RandomDice {

    /** The number of sides on the die. */
    private final int sides;

    /**
     * Constructs a RandomDice with the specified number of sides.
     *
     * @param sides The number of sides on the die. Must be greater than 0.
     * @throws IllegalArgumentException if sides is less than 1.
     */
    public RandomDice(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("Number of sides must be at least 1");
        }
        this.sides = sides;
    }

    /**
     * Returns the number of sides on the die.
     *
     * @return The number of sides.
     */
    public int getSides() {
        return sides;
    }

    /**
     * Rolls the die and returns a result.
     *
     * @return A result from rolling the die.
     */
    public abstract int roll();

}