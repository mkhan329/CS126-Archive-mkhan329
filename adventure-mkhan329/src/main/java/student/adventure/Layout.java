package student.adventure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
/**
 * This class is used to hold the general layout of the Krusty Krab JSON, and all the rooms.
 */
public class Layout {
    @JsonProperty("startingRoom")
    private String startingRoom;

    @JsonProperty("endingRoom")
    private String endingRoom;

    @JsonProperty("rooms")
    private List<Room> rooms;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setStartingRoom(String room) {
        startingRoom = room;
    }

    public void setEndingRoom(String room) {
        endingRoom = room;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
