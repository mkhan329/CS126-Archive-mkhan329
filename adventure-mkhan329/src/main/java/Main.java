import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import student.adventure.GameEngine;
import student.adventure.Layout;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.File;
import java.io.IOException;

public class Main {
    // Wishing you good luck on your Adventure!
    /**
     * This main class is where the Krusty Krab JSON is deserialized, and then used to start the adventure game.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = AdventureServer.createServer(AdventureResource.class);
        server.start();
        GameEngine game = new GameEngine(new File("src/main/resources/krustyKrab.json"));
        game.startGame();
    }
}
