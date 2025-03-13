package student.adventure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import student.server.AdventureState;
import student.server.GameStatus;

import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * This Game Engine class is used to run the Krusty Krab Game, from the respective JSON file.
 */
public class GameEngine {

    private Layout layout;
    private Room currentRoom;
    private List<Room> roomsEntered = new ArrayList<Room>();
    private List<String> inventory = new ArrayList<String>();
    private boolean playing = false;
    private Map<String, Room> gameMap = new HashMap<String, Room>();

    private final int VERB = 0;     //These constants are used for separating
    private final int OBJECT = 1;   //strings into their verb and the object of the verb

    /**
     * Constructor used to initialize game engine.
     * @param layout the layout specifying the rooms of the given map.
     */
    public GameEngine (Layout layout) {
        assert layout != null : "Layout cannot be null!";
        this.layout = layout;
        for(Room r : layout.getRooms()) {
            gameMap.put(r.getName(), r);
        }
        currentRoom = gameMap.get(layout.getStartingRoom());
        roomsEntered.add(currentRoom);
    }

    /**
     * Constructor used to initialize game engine.
     * @param file the file for the layout specifying the rooms of the given map.
     */
    public GameEngine (File file) throws IOException {
        assert file != null : "File cannot be null!";
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.layout = objectMapper.readValue(file, Layout.class);;
        for(Room r : layout.getRooms()) {
            gameMap.put(r.getName(), r);
        }
        currentRoom = gameMap.get(layout.getStartingRoom());
        roomsEntered.add(currentRoom);
    }

    /**
     * Moves player object to the room in the specified direction.
     * @param object object of the verb, specifies direction
     */
    private void go(String object) {
        if (object == null || object.length() == 0) {
            System.out.println("(blank) isn't a direction!");
            return;
        }
        boolean roomChange = false;
        for (Direction d : currentRoom.getDirections()) {
            if (d.getDirectionName().toLowerCase().equals(object)) {
                currentRoom = gameMap.get(d.getRoom());
                roomChange = true;
            }
        }
        if (!roomChange) {
            System.out.println(object + " isn't a direction!");
        } else {
            roomsEntered.add(currentRoom);
            System.out.println(currentRoom.toString());
        }
    }

    /**
     * Takes whatever item is specified by the user.
     * @param object of the verb, specifies item.
     */
    private void take(String object) {
        if (object == null || object.length() == 0) {
            System.out.println("(blank) isn't here!");
            return;
        }
        boolean itemTaken = false;
        for (String item : currentRoom.getItems()) {
            if (item.equals(object)) {
                currentRoom.removeItem(object);
                inventory.add(object);
                itemTaken = true;
                break;
            }
        }
        if (!itemTaken) {
            System.out.println(object + " isn't here!");
        }
    }

    /**
     * Drops whatever item the user specifies fromt their inventory.
     * @param object object of the verb, specifies item.
     */
    private void drop(String object) {
        if (object == null || object.length() == 0) {
            System.out.println("(blank) isn't in your invetory!");
            return;
        }
        boolean itemDropped = false;
        for (String item : inventory) {
            if (item.equals(object)) {
                inventory.remove(object);
                currentRoom.addItem(object);
                itemDropped = true;
                break;
            }
        }
        if (!itemDropped) {
            System.out.println(object + " isn't in your invetory!");
        }
    }

    /**
     * Examines room by printing the room's information.
     * @param object object of the verb, should be empty.
     */
    private void examine(String object) {
        if (object != null && object.length() != 0) {
            System.out.println("I dont understand " + '"' + "examine " + object + '"');
        } else {
            System.out.println(currentRoom.toString());
        }
    }

    /**
     * Quits game, called when user types "quit" or "exit".
     * @param object object of the verb, should be empty.
     */
    private void quit(String object) {
        if (object != null && object.length() != 0) {
            System.out.println("I dont understand " + '"' + "quit " + object + '"');
        } else {
            playing = false;
        }
    }

    /**
     * Reads player input, calls helper method to parse input, and sends parsed input to methods : go, take, drop, examine, quit.
     * @param scannedInput the scanned input of the user.
     */
    public void readPlayerAction(String scannedInput) {
        assert scannedInput != null : "Input cannot be null!";
        String[] input = prepString(scannedInput);
        String verb = input[VERB];
        String object = input[OBJECT];
        if(verb.equals("go")) {
            go(object);
        }
        else if(verb.equals("take")) {
            take(object);
        }
        else if(verb.equals("drop")) {
            drop(object);
        }
        else if(verb.equals("examine")) {
            examine(object);
        }
        else if(verb.equals("quit") || verb.equals("exit")) {
            quit(object);
        }
        else {
            System.out.println("I dont understand " + '"' + verb + " " + object + '"');
        }

    }

    /**
     * Takes a string and converts it into a readable format for method readPlayerAction, by converting it into a string array with a verb and a noun.
     * @param i the string to be prepped.
     */
    private static String[] prepString(String i) {
        String input = i;
        input = input.toLowerCase();
        input = input.trim();
        String verb = input.split("\\s+")[0];
        String object = input.substring(input.split("\\s+")[0].length());
        object = object.trim();
        String[] output = new String[2];
        output[0] = verb;
        output[1] = object;
        return output;
    }

    /**
     * Used to initialize the game and start the playing-loop.
     */
    public void startGame() {
        playing = true;
        Scanner scan = new Scanner(System.in);
        System.out.println(currentRoom.toString());
        while (playing) {
            if (currentRoom.getName().equals(layout.getEndingRoom())) {
                playing = false;
                System.out.println("You've made it to the Krusty Krab Dumpster; you win!\n");
                break;
            }
            System.out.print("> ");
            readPlayerAction(scan.nextLine());
        }
        System.out.println("Rooms visited in order: " + getRoomsEntered().toString());
    }

    /**
     * Used to print the stored array of rooms that the player has entered, in order.
     */
    public List<String> getRoomsEntered() {
        List<String> roomsEnteredEnd = new ArrayList<String>();
        for (Room r : roomsEntered) {
            roomsEnteredEnd.add(r.getName());
        }
        return roomsEnteredEnd;
    }

    /**
     * Used to return the current game status of the given player id.
     * @param id the id# of the player.
     */
    public GameStatus getGameStatus(int id) {
        Map<String, List<String>> commandOptions = new HashMap<String, List<String>>();
        if (currentRoom.getName().equals(layout.getEndingRoom())) {
            String roomsEnteredEnd = "";
            for(String r: getRoomsEntered()) {
                roomsEnteredEnd += "\n" + r + "\n";
            }
            return new GameStatus(false, id, "You've made it to the Krusty Krab Dumpster; you win!\n Rooms visited in order: \n"
                    + roomsEnteredEnd, currentRoom.getImageLink(), currentRoom.getYoutubeLink(), new AdventureState(), commandOptions);
        }
        List<String> directions = new ArrayList<String>();
        for (Direction d : currentRoom.getDirections()) {
            directions.add(d.toString());
        }
        commandOptions.put("go", directions);
        commandOptions.put("take", currentRoom.getItems());
        commandOptions.put("drop", inventory);
        return new GameStatus(false, id, currentRoom.toString(), currentRoom.getImageLink(), currentRoom.getYoutubeLink(), new AdventureState(), commandOptions);
    }
}
