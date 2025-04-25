package me.yattaw.bulldog.core.model;

import me.yattaw.bulldog.core.players.Player;

import java.util.ArrayList;
import java.util.Optional;

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

    public void removeAll() {
        players.clear();
    }

    /**
     * Retrieves a Player object by the specified name.
     *
     * @param name The name of the player to retrieve.
     * @return An Optional containing the Player object if found; otherwise, an empty Optional.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public Optional<Player> getPlayerByName(String name) {
        // Validate the input name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        // Iterate through the list of players to find a match
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return Optional.of(player); // Return the player wrapped in an Optional
            }
        }

        // Return an empty Optional if no player with the specified name is found
        return Optional.empty();
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
     * Retrieves the total number of players in the model.
     *
     * @return The number of players currently stored in the model.
     */
    public int getPlayerCount() {
        return players.size();
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