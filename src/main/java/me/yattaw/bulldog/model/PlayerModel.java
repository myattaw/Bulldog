package me.yattaw.bulldog.model;

import me.yattaw.bulldog.players.Player;

import java.util.ArrayList;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * PlayerModel class: Manages a list of Player objects and provides methods
 * for adding, retrieving, and updating player data.
 */
public class PlayerModel {

    /**
     * List to store Player objects.
     */
    private ArrayList<Player> players;

    /**
     * Constructs a new PlayerModel with an empty list of players.
     */
    public PlayerModel() {
        this.players = new ArrayList<>();
    }

    /**
     * Adds a new player to the list.
     *
     * @param player The Player object to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Retrieves the name of a player at the specified index.
     *
     * @param index The index of the player in the list.
     * @return The player's name if the index is valid; otherwise, throws an exception.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public String getPlayerName(int index) {
        if (index < 0 || index >= players.size()) {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
        return players.get(index).getName();
    }

    /**
     * Retrieves the score of a player at the specified index.
     *
     * @param index The index of the player in the list.
     * @return The player's score if the index is valid; otherwise, throws an exception.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public int getPlayerScore(int index) {
        if (index < 0 || index >= players.size()) {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
        return players.get(index).getScore();
    }

    /**
     * Updates the score of a player at the specified index.
     *
     * @param index The index of the player in the list.
     * @param newScore The new score to assign to the player.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public void setPlayerScore(int index, int newScore) {
        if (index < 0 || index >= players.size()) {
            throw new IndexOutOfBoundsException("Invalid player index: " + index);
        }
        players.get(index).setScore(newScore);
    }

    /**
     * Returns the full list of players.
     *
     * @return An ArrayList of Player objects.
     */
    public ArrayList<Player> getAllPlayers() {
        return new ArrayList<>(players); // Return a copy to protect data integrity
    }

}