package me.yattaw.bulldog.data;

import me.yattaw.bulldog.players.types.SevenPlayer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * FakeRandomTest Class: JUnit tests for FakeRandom and SevenPlayer.
 * Written with assistance from AI tools.
 */
class FakeRandomTest {

    // ======================
    // FakeRandom Core Tests
    // ======================
    @Test
    void fakeRandom_returnsSequenceExactly() {
        List<Integer> fixedValues = Arrays.asList(1, 2, 3, 4);
        FakeRandom dice = new FakeRandom(6, fixedValues.iterator());

        assertEquals(1, dice.roll());
        assertEquals(2, dice.roll());
        assertEquals(3, dice.roll());
        assertEquals(4, dice.roll());
    }

    @Test
    void fakeRandom_wrapsLargeValuesUsingModulo() {
        List<Integer> values = Arrays.asList(7, 8, 12);
        FakeRandom dice = new FakeRandom(6, values.iterator());

        assertEquals(1, dice.roll()); // 7 → (7-1) % 6 + 1 = 1
        assertEquals(2, dice.roll()); // 8 → (8-1) % 6 + 1 = 2
        assertEquals(6, dice.roll()); // 12 → (12-1) % 6 + 1 = 6
    }

    @Test
    void fakeRandom_throwsWhenSequenceExhausted() {
        FakeRandom dice = new FakeRandom(6, List.of(1).iterator());
        dice.roll(); // First roll is valid
        assertThrows(IllegalStateException.class, dice::roll); // Second roll should throw
    }

    // ======================
    // SevenPlayer Tests
    // ======================
    @Test
    void sevenPlayer_playMethodExists() {
        SevenPlayer player = new SevenPlayer();
        assertDoesNotThrow(player::play);
    }

    @Test
    void sevenPlayer_returnsNonNegativeScore() {
        SevenPlayer player = new SevenPlayer();
        int score = player.play();
        assertTrue(score >= 0, "Score should be non-negative");
    }

    @Test
    void sevenPlayer_returnsZeroWhenSixRolled() {
        List<Integer> fixedValues = Arrays.asList(1, 6); // Roll a 1, then a 6
        FakeRandom fakeDice = new FakeRandom(6, fixedValues.iterator());
        SevenPlayer player = new SevenPlayer();
        player.setDice(fakeDice);

        int score = player.play();
        assertEquals(0, score, "Score should be 0 when a 6 is rolled");
    }

    @Test
    void sevenPlayer_stopsAtSevenOrMore() {
        List<Integer> fixedValues = Arrays.asList(4, 3); // 4 + 3 = 7
        FakeRandom fakeDice = new FakeRandom(6, fixedValues.iterator());
        SevenPlayer player = new SevenPlayer();
        player.setDice(fakeDice);

        int score = player.play();
        assertEquals(7, score, "Score should be 7 when target is reached");
    }

    @Test
    void sevenPlayer_accumulatesUntilTarget() {
        List<Integer> fixedValues = Arrays.asList(2, 3, 4); // 2 + 3 + 4 = 9
        FakeRandom fakeDice = new FakeRandom(6, fixedValues.iterator());
        SevenPlayer player = new SevenPlayer();
        player.setDice(fakeDice);

        int score = player.play();
        assertEquals(9, score, "Score should accumulate to 9, exceeding target");
    }

}