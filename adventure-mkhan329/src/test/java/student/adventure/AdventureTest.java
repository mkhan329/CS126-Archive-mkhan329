package student.adventure;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import student.server.AdventureException;
import student.server.AdventureState;
import student.server.Command;
import student.server.GameStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventureTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    static File file = new File("src/main/resources/krustyKrab.json");
    static Layout layout;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        // This is run before every test.
        try {
            layout = objectMapper.readValue(file, Layout.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testReadActionNull() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Input cannot be null!");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction(null);
    }

    @Test
    public void testNullLayout() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Layout cannot be null!");
        GameEngine game = new GameEngine((Layout) null);
    }

    @Test
    public void testNullFile() throws IOException {
        exception.expect(AssertionError.class);
        exception.expectMessage("File cannot be null!");
        GameEngine game = new GameEngine((File) null);
    }

    @Test
    public void testReadActionEmpty() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("");
        String action = "I dont understand \" \"";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testExamineInvalidInput() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("examine cat8123   9080129)#(*12)*");
        String action = "I dont understand \"examine cat8123   9080129)#(*12)*\"";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testExamine() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("examine");
        String action = "You are in the kitchen of the Chum Bucket.\n" +
                "From here, you can go: [North]\n" +
                "Items visible: []";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testExamineWithSpaces() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("     examine      ");
        String action = "You are in the kitchen of the Chum Bucket.\n" +
                "From here, you can go: [North]\n" +
                "Items visible: []";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testExamineSecondRoom() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("examine");
        String action = "You are in the entrance of the Chum Bucket.\n" +
                "From here, you can go: [North, South]\n" +
                "Items visible: [hat, gloves]";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testExamineFinalRoom() {
        layout.setStartingRoom("KrustyKrabDumpster");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("examine");
        String action = "You are in the Krusty Krab Dumpster.\n" +
                "From here, you can go: [South]\n" +
                "Items visible: []";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testGoNextRoom() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("go north");
        String action = "You are in the entrance of the Chum Bucket.\n" +
                "From here, you can go: [North, South]\n" +
                "Items visible: [hat, gloves]";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testGoWithSpaces() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("       go        north        ");
        String action = "You are in the entrance of the Chum Bucket.\n" +
                "From here, you can go: [North, South]\n" +
                "Items visible: [hat, gloves]";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testGoBackwards() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("go south");
        String action = "You are in the kitchen of the Chum Bucket.\n" +
                "From here, you can go: [North]\n" +
                "Items visible: []";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testGoFinalRoom() {
        layout.setStartingRoom("SpongebobsKitchen");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("go north");
        String action = "You are in the Krusty Krab Dumpster.\n" +
                "From here, you can go: [South]\n" +
                "Items visible: []";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testGoInvalid() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("       go        nor       th        ");
        String action = "nor       th isn't a direction!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testTake() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("       TAKE           HAT           ");
        game.readPlayerAction("ExAmiNe");
        String action = "You are in the entrance of the Chum Bucket.\n" +
                "From here, you can go: [North, South]\n" +
                "Items visible: [gloves]";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testTakeAlreadyTaken() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("take          hat");
        game.readPlayerAction("    take      hat");
        String action = "hat isn't here!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testTakeInvalid() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("take hat");
        String action = "hat isn't here!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testTakeEmpty() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("take ");
        String action = "(blank) isn't here!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testDrop() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("take hat");
        game.readPlayerAction("drop hat");
        game.readPlayerAction("examine");
        String action =
                "You are in the entrance of the Chum Bucket.\n" +
                "From here, you can go: [North, South]\n" +
                "Items visible: [gloves, hat]";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testDropAlreadyDropped() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("take hat");
        game.readPlayerAction("drop hat");
        game.readPlayerAction("drop hat");
        String action =
                "hat isn't in your invetory!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testDropInvalid() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("drop hat");
        String action = "hat isn't in your invetory!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testDropEmpty() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("DrOp  ");
        String action = "(blank) isn't in your invetory!";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testTakeAndDropInBathroom() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("go north");
        game.readPlayerAction("take hat");
        game.readPlayerAction("take gloves");
        game.readPlayerAction("go north");
        game.readPlayerAction("go east");
        game.readPlayerAction("take patrick");
        game.readPlayerAction("go west");
        game.readPlayerAction("go north");
        game.readPlayerAction("go left");
        game.readPlayerAction("take formula");
        game.readPlayerAction("go south");
        game.readPlayerAction("go forward");
        game.readPlayerAction("take coin");
        game.readPlayerAction("go south");
        game.readPlayerAction("go right");
        game.readPlayerAction("drop hat");
        game.readPlayerAction("drop gloves");
        game.readPlayerAction("drop patrick");
        game.readPlayerAction("drop coin");
        game.readPlayerAction("drop formula");
        game.readPlayerAction("examine");
        String action =
                "You are in the bathroom.\n" +
                "From here, you can go: [South]\n" +
                "Items visible: [hat, gloves, patrick, coin, formula]";
        assertThat(outContent.toString().trim(), containsString(action));
    }

    @Test
    public void testRandomInput() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("faflalaf!@#    $#$!@$lasfl");
        String action = "I dont understand \"faflalaf!@# $#$!@$lasfl\"";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testQuit() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("quit");
        String action = "";
        assertEquals(action.trim(), outContent.toString());
    }

    @Test
    public void testQuitInvalid() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("quit      banananananafj908190381290&()*&@(#*&()");
        String action = "I dont understand \"quit banananananafj908190381290&()*&@(#*&()\"";
        assertEquals(action.trim(), outContent.toString().trim());
    }

    @Test
    public void testRoomHistory() {
        GameEngine game = new GameEngine(layout);
        game.readPlayerAction("go north");
        game.readPlayerAction("go north");
        game.readPlayerAction("go east");
        game.readPlayerAction("go west");
        game.readPlayerAction("go north");
        game.readPlayerAction("go south");
        game.readPlayerAction("go north");
        game.readPlayerAction("go south");
        game.readPlayerAction("go north");
        game.readPlayerAction("go south");
        game.readPlayerAction("go north");
        game.readPlayerAction("go forward");
        String rooms = "[ChumBucketKitchen, ChumBucketEntrance, Road, PatrickOnGround, Road, KrustyKrabEntrance, Road, KrustyKrabEntrance, Road, KrustyKrabEntrance, Road, KrustyKrabEntrance, SquidwardsRegister]";
        System.out.println(game.getRoomsEntered().toString());
        assertThat(outContent.toString(), containsString(rooms));
    }

    @Test
    public void testGetGameStatus() {
        GameEngine game = new GameEngine(layout);
        Map<String, List<String>> commandOptions = new HashMap<String, List<String>>();
        List<String> directions = new ArrayList<String>();
        for (Direction d : layout.getRooms().get(0).getDirections()) {
            directions.add(d.toString());
        }
        commandOptions.put("go", directions);
        commandOptions.put("take", layout.getRooms().get(0).getItems());
        commandOptions.put("drop", new ArrayList<String>());
        GameStatus gameStatus = new GameStatus(false, 0, layout.getRooms().get(0).toString(), layout.getRooms().get(0).getImageLink(), layout.getRooms().get(0).getYoutubeLink(), new AdventureState(), commandOptions);
        assertEquals(gameStatus.getMessage(), game.getGameStatus(0).getMessage());
        assertEquals(gameStatus.getCommandOptions(), game.getGameStatus(0).getCommandOptions());
        assertEquals(gameStatus.getId(), game.getGameStatus(0).getId());
        assertEquals(gameStatus.getImageUrl(), game.getGameStatus(0).getImageUrl());
        assertEquals(gameStatus.getVideoUrl(), game.getGameStatus(0).getVideoUrl());
    }

    @Test
    public void testGetGameStatusNextRoom() {
        layout.setStartingRoom("ChumBucketEntrance");
        GameEngine game = new GameEngine(layout);
        Map<String, List<String>> commandOptions = new HashMap<String, List<String>>();
        List<String> directions = new ArrayList<String>();
        for (Direction d : layout.getRooms().get(1).getDirections()) {
            directions.add(d.toString());
        }
        commandOptions.put("go", directions);
        commandOptions.put("take", layout.getRooms().get(1).getItems());
        commandOptions.put("drop", new ArrayList<String>());
        GameStatus gameStatus = new GameStatus(false, 0, layout.getRooms().get(1).toString(), layout.getRooms().get(1).getImageLink(), layout.getRooms().get(1).getYoutubeLink(), new AdventureState(), commandOptions);
        assertEquals(gameStatus.getMessage(), game.getGameStatus(0).getMessage());
        assertEquals(gameStatus.getCommandOptions(), game.getGameStatus(0).getCommandOptions());
        assertEquals(gameStatus.getId(), game.getGameStatus(0).getId());
        assertEquals(gameStatus.getImageUrl(), game.getGameStatus(0).getImageUrl());
        assertEquals(gameStatus.getVideoUrl(), game.getGameStatus(0).getVideoUrl());
    }

    @Test
    public void testServiceReset() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        assertEquals(service.getIdOffset(), 1);
        service.reset();
        assertEquals(service.getIdOffset(), 0);
    }

    @Test
    public void testServiceNewGame() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        service.newGame();
        assertEquals(service.getIdOffset(), 2);
    }

    @Test
    public void testServiceGetGame() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        GameEngine game = new GameEngine(layout);
        assertEquals(service.getGame(1).getMessage(), game.getGameStatus(1).getMessage());
        assertEquals(service.getGame(1).getCommandOptions(), game.getGameStatus(1).getCommandOptions());
        assertEquals(service.getGame(1).getId(), game.getGameStatus(1).getId());
        assertEquals(service.getGame(1).getImageUrl(), game.getGameStatus(1).getImageUrl());
        assertEquals(service.getGame(1).getVideoUrl(), game.getGameStatus(1).getVideoUrl());
    }

    @Test
    public void testServiceDestroyGame() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        assertEquals(service.destroyGame(1), true);
    }

    @Test
    public void testServiceExecute() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        service.executeCommand(1, new Command("go", "north"));
        assertEquals(service.getGame(1).getVideoUrl(), "https://www.youtube.com/watch?v=3eVORWdzMfE&ab_channel=StupidRich");
    }

    @Test
    public void testServiceGetGameInvalid() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        exception.expect(AssertionError.class);
        exception.expectMessage("Invalid ID");
        service.getGame(-1);
    }
    @Test
    public void testServiceGetGameInvalidLarge() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        exception.expect(AssertionError.class);
        exception.expectMessage("Invalid ID");
        service.getGame(10);
    }

    @Test
    public void testServiceExecuteInvalid() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        exception.expect(AssertionError.class);
        exception.expectMessage("Invalid ID");
        service.executeCommand(-10, new Command("go", "north"));
    }

    @Test
    public void testServiceDestroyGameFalse() throws AdventureException {
        SpongebobAdventureService service = new SpongebobAdventureService();
        service.newGame();
        assertEquals(service.destroyGame(-1), false);
    }

}