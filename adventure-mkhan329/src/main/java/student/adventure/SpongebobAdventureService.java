package student.adventure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import student.server.AdventureException;
import student.server.AdventureService;
import student.server.Command;
import student.server.GameStatus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class SpongebobAdventureService implements AdventureService {
    private Map<Integer, GameEngine> map = new HashMap<Integer, GameEngine>();
    private int idOffset = 0;
    private File file = new File("src/main/resources/krustyKrab.json");

    public int getIdOffset() {
        return idOffset;
    }

    /**
     * Resets the service to its initial state.
     */
    @Override
    public void reset() {
        map = new HashMap<Integer, GameEngine>();
        idOffset = 0;
    }

    /**
     * Creates a new Adventure game and stores it.
     *
     * @return the id of the game.
     */
    @Override
    public int newGame() throws AdventureException {
        idOffset++;
        try {
            map.put(idOffset, new GameEngine(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idOffset;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     *
     * @param id the instance id
     * @return the current state of the game
     */
    @Override
    public GameStatus getGame(int id) {
        assert id >= 1 && id <= idOffset : "Invalid ID";
        return map.get(id).getGameStatus(id);
    }

    /**
     * Removes & destroys a game instance with the given ID.
     *
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    @Override
    public boolean destroyGame(int id) {
        if (map.get(id) == null) {
            return false;
        }
        map.remove(id);
        return true;
    }

    /**
     * Executes a command on the game instance with the given id, changing the game state if applicable.
     *
     * @param id      the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        assert id >= 1 && id <= idOffset : "Invalid ID";
        map.get(id).readPlayerAction(command.getCommandName() + " " + command.getCommandValue());
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     *
     * @return a sorted map of player names to scores
     */
    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
