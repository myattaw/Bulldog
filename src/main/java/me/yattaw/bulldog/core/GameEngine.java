package me.yattaw.bulldog.core;

import me.yattaw.bulldog.core.players.Player;
import me.yattaw.bulldog.reflection.ReflectionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * GameEngine class: Core class for managing the Bulldog game's logic
 * Manages matches, players, and gameplay until a player reaches the winning score.
 */
public class GameEngine {

    /** The winning score required to win the game. */
    public static final int WINNING_SCORE = 104;

    /** Map to store match names and their associated players. */
    private final Map<String, List<Player>> matchPlayers = new HashMap<>();

    /**
     * Manages gameplay for a match until a player reaches or exceeds the winning score.
     * @param matchName The name of the match to play
     */
    public void playUntilWinner(String matchName) {
        List<Player> playerList = getMatchPlayers().get(matchName);

        if (playerList == null || playerList.isEmpty()) {
            System.out.println("No players in the match.");
            return;
        }

        while (true) {
            for (Player player : playerList) {
                int roundScore = player.play();
                player.setScore(player.getScore() + roundScore);

                if (player.getScore() >= WINNING_SCORE) {
                    return;
                }
            }
        }
    }

    /**
     * Creates a new player of the specified type and adds them to the match.
     * @param match The name of the match to add the player to
     * @param playerType The type of player to create
     * @return True if the player was created and added, false if invalid type
     */
    public boolean createPlayer(String match, String playerType) {
        Player player = ReflectionHelper.createPlayerInstance(playerType);
        if (player == null) {
            return false;
        }
        return matchPlayers.get(match).add(player);
    }

    /**
     * Creates a new match with the specified name.
     * @param matchName Name of the new match
     */
    public void createMatch(String matchName) {
        matchPlayers.put(matchName, new ArrayList<>());
    }

    /**
     * Gets the players for a specific match.
     * @param matchName Name of the match
     * @return List of players or null if match doesn't exist
     */
    public List<Player> getPlayersForMatch(String matchName) {
        return matchPlayers.get(matchName);
    }

    /**
     * Returns the map containing all matches and their players.
     * @return Map of match names to player lists
     */
    public Map<String, List<Player>> getMatchPlayers() {
        return matchPlayers;
    }

    /**
     * Gets the current score for a player in a match.
     * @param matchName Name of the match
     * @param playerIndex Index of the player
     * @return Current score or -1 if invalid
     */
    public int getPlayerScore(String matchName, int playerIndex) {
        List<Player> players = matchPlayers.get(matchName);
        if (players == null || playerIndex < 0 || playerIndex >= players.size()) {
            return -1;
        }
        return players.get(playerIndex).getScore();
    }

    /**
     * Gets the name of a player in a match.
     * @param matchName Name of the match
     * @param playerIndex Index of the player
     * @return Player name or null if invalid
     */
    public String getPlayerName(String matchName, int playerIndex) {
        List<Player> players = matchPlayers.get(matchName);
        if (players == null || playerIndex < 0 || playerIndex >= players.size()) {
            return null;
        }
        return players.get(playerIndex).getName();
    }

}