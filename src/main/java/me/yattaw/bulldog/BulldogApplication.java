package me.yattaw.bulldog;

import me.yattaw.bulldog.data.Dice;
import me.yattaw.bulldog.players.Player;
import me.yattaw.bulldog.players.types.*;

import java.util.*;

/**
 * Michael Yattaw
 * Login ID: michael.yattaw@maine.edu
 * COS 420, Spring 2025
 * Bulldog Project
 * Bulldog class: Main class for the Bulldog game
 * Manages matches, players, and gameplay until a player reaches the winning score.
 */
public class BulldogApplication {

    /** The winning score required to win the game. */
    public static final int WINNING_SCORE = 104;

    /** Map to store match names and their associated players. */
    private final Map<String, List<Player>> matchPlayers = new HashMap<>();

    /**
     * Entry point for the Bulldog game.
     * Initializes a match, prompts the user to input the match name, number of players,
     * and player types. Then starts the game and continues until a player wins.
     *
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        BulldogApplication bulldog = new BulldogApplication();
        Scanner scanner = new Scanner(System.in);

        // Create a new match
        System.out.println("Enter match name: ");
        String matchName = scanner.nextLine();

        // Enter number of players per match
        int playerCount = 0;
        while (playerCount <= 0) {
            System.out.println("Enter number of players: ");
            try {
                playerCount = Integer.parseInt(scanner.nextLine());
                if (playerCount <= 0) {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Create new match data
        bulldog.getMatchPlayers().put(matchName, new ArrayList<>(playerCount));

        // Add new players to match
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Enter type for player " + (i + 1) + " (ai, fifteen, human, random, unique, wimp): ");
            String playerType = scanner.nextLine().toLowerCase();
            while (!bulldog.createPlayer(matchName, playerType)) {
                System.out.println("Invalid player type. Please enter one of: fifteen, human, random, unique.");
                playerType = scanner.nextLine().toLowerCase();
            }
        }

        bulldog.playUntilWinner(matchName);
    }

    /**
     * Manages gameplay for a match until a player reaches or exceeds the winning score.
     * Players take turns rolling the die, and their scores are updated and displayed
     * after each turn. The game ends when a player's score reaches the WINNING_SCORE.
     *
     * @param matchName The name of the match to play
     */
    public void playUntilWinner(String matchName) {
        List<Player> playerList = getMatchPlayers().get(matchName);

        if (playerList == null || playerList.isEmpty()) {
            System.out.println("No players in the match.");
            return;
        }

        while (true) {
            for (int i = 0; i < playerList.size(); i++) {
                Player player = playerList.get(i);
                int roundScore = player.play();
                player.setScore(player.getScore() + roundScore);

                // Print current scores of all players
                System.out.println();
                System.out.printf("After Player %d '%s' scored %d points, the current scores are:%n", i + 1, player.getName(), roundScore);
                for (int j = 0; j < playerList.size(); j++) {
                    Player p = playerList.get(j);
                    System.out.printf(" - Player %d '%s': %d points%n", j + 1, p.getName(), p.getScore());
                }
                System.out.println();

                if (player.getScore() >= WINNING_SCORE) {
                    System.out.printf("Player %d '%s' won against %d other players with a score of %d%n", i + 1, player.getName(), playerList.size() - 1, player.getScore());
                    return;
                }
            }
        }
    }

    /**
     * Creates a new player of the specified type and adds them to the match.
     * Valid player types are "ai", "fifteen", "human", "random", "unique", and "wimp".
     * Returns false if the player type is invalid.
     *
     * @param match The name of the match to add the player to
     * @param playerType The type of player to create
     * @return True if the player was created and added, false if the player type is invalid
     */
    private boolean createPlayer(String match, String playerType) {
        Player player;
        switch (playerType) {
            case "ai" -> player = new AIUniquePlayer("AICodedPlayer");
            case "fifteen" -> player = new FifteenPlayer("FifteenPlayer");
            case "human" -> player = new HumanPlayer("HumanPlayer");
            case "random" -> player = new RandomPlayer("RandomPlayer");
            case "unique" -> player = new UniquePlayer("UniquePlayer");
            case "wimp" -> player = new WimpPlayer("WimpPlayer");
            default -> {
                return false;
            }
        }
        return matchPlayers.get(match).add(player);
    }

    /**
     * Returns the map containing all matches and their associated players.
     *
     * @return A map where the key is the match name and the value is a list of players in the match
     */
    public Map<String, List<Player>> getMatchPlayers() {
        return matchPlayers;
    }

}