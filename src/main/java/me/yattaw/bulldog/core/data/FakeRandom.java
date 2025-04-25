package me.yattaw.bulldog.core.data;

import java.util.Iterator;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * FakeRandom Class: Implementation of RandomDice that returns predetermined values from a sequence.
 * Each roll returns the next value in the sequence, adjusted to fit within the die's number of sides.
 * Values that exceed the number of sides are wrapped using modulo arithmetic.
 */
public class FakeRandom extends RandomDice {

    private Iterator<Integer> valueIterator;

    /**
     * Constructs a FakeRandom die with specified sides and value sequence
     * @param sides Number of sides (must be ≥1)
     * @param values Iterator providing the sequence of values
     */
    public FakeRandom(int sides, Iterator<Integer> values) {
        super(sides);
        this.valueIterator = values;
    }

    /**
     * Returns the next value from the sequence, adjusted for die sides
     * @return Roll result (1 to sides)
     * @throws IllegalStateException if no more values available
     */
    @Override
    public int roll() {
        if (!valueIterator.hasNext()) {
            throw new IllegalStateException("No more values in sequence");
        }

        int nextVal = valueIterator.next();
        // Convert value to 1..sides range
        return ((nextVal - 1) % getSides()) + 1;
    }

}